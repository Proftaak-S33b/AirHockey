/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Joris
 */
public class Server {

    private ArrayList<Server.ConnectionToClient> clientList;
    private LinkedBlockingQueue<Object> messages;
    private ServerSocket serverSocket;

    public Server() {
        clientList = new ArrayList<>();
        messages = new LinkedBlockingQueue<>();
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }

        Thread accept = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket s = serverSocket.accept();
                        clientList.add(new ConnectionToClient(s));
                    } catch (IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                    }
                }
            }
        };
        accept.setDaemon(true);
        accept.start();

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object message = messages.take();
                        sendToAll(message);
                        System.out.println("Message received");
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException: " + e.getMessage());
                    }
                }
            }
        };
        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private class ConnectionToClient {

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToClient(Socket socket) throws IOException {
            this.socket = socket;
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            Thread read = new Thread() {
                @Override
                public void run() {
                    try {
                        Object message;
                        while ((message = in.readObject()) != null) {
                            messages.put(message);
                        }
                    } catch (IOException | InterruptedException ex) {
                        System.out.println("CTC Error:" + ex.getMessage());
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            };

            read.setDaemon(true); // terminate when main ends
            read.start();
        }

        public void write(Object message) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void sendToAll(Object message) {
        for (ConnectionToClient client : clientList) {
            client.write(message);
        }
    }
}
