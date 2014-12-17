/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.application.Platform;
import javafx.scene.control.ListView;

/**
 *
 * @author Joris
 */
public class ChatSocketClient {

    private ConnectionToServer server;
    private LinkedBlockingQueue<String> messages;
    private Socket socket;
    private ListView chatBox;

    public ChatSocketClient(String IPAddress, int port, ListView chatBox) throws IOException {
        socket = new Socket(IPAddress, port);
        messages = new LinkedBlockingQueue<>();
        server = new ConnectionToServer(socket);
        this.chatBox = chatBox;

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = messages.take();
                        Platform.runLater(() -> {
                            chatBox.getItems().add(message);
                        });
                        System.out.println("Message Received: " + message);
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
                    while (true) {
                        try {
                            String message = (char) in.read() + "";
                            messages.put(message);
                        } catch (IOException | InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            };

            read.setDaemon(true);
            read.start();
        }

        private void write(String message) {
            out.println(message);
        }
    }

    public void send(String message) {
        server.write(message);
    }
}
