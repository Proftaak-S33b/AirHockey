/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;

/**
 *
 * @author Joris
 */
public class Client {

    private ConnectionToServer server;
    private LinkedBlockingQueue<String> messages;
    private Socket socket;
    private ReadOnlyStringWrapper observableMessage;

    public Client(String IPAddress, int port, ChangeListener<String> changeListener) throws IOException {
        socket = new Socket(IPAddress, port);
        messages = new LinkedBlockingQueue<>();
        server = new ConnectionToServer(socket);
        observableMessage = new ReadOnlyStringWrapper();
        observableMessage.addListener(changeListener);

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = messages.take();
                        observableMessage.set(message);
                        //System.out.println("Message: " + message);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException: " + e.getMessage());
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private class ConnectionToServer {

        BufferedReader in;
        PrintWriter out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread read = new Thread() {
                @Override
                public void run() {
                    try {
                        String message = in.readLine();
                        while (message != null) {
                            messages.put(message);
                            message = in.readLine();
                        }
                    } catch (IOException | InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            };

            read.setDaemon(true);
            read.start();
        }

        private void write(String message) {
            out.println(message);
            out.flush();
        }
    }

    public void send(String message) {
        server.write(message);
    }
}
