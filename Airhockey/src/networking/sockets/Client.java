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
import networking.GameData;

/**
 *
 * @author Joris
 */
public class Client {

    private ConnectionToServer server;
    private LinkedBlockingQueue<Object> messages;
    private Socket socket;
    private ReadOnlyStringWrapper observableMessage;
    private GameData gameData;

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
                        Object message = messages.take();
                        if (message instanceof Message) {
                            observableMessage.set(message.toString());
                        } else if (message instanceof GameData) {
                            gameData = (GameData) message;
                        } else {
                            System.out.println("Unknown class type received");
                        }
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

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {
            this.socket = socket;
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            Thread read = new Thread() {
                @Override
                public void run() {
                    try {
                        Object message = in.readObject();
                        while (message != null) {
                            messages.put(message);
                            message = in.readObject();
                        }
                    } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            };
            read.setDaemon(true);
            read.start();
        }

        private void write(Object message) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void send(Object message) {
        server.write(message);
    }

    public void sendCoordinate(float Podx, float Pody, SocketCoordinate.Name name) {
        send(new SocketCoordinate(Podx, Pody, name));
    }
}
