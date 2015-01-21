/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import game.Human;
import networking.sockets.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import networking.GameData;

/**
 *
 * @author Joris
 */
public class Server {

    private ArrayList<Connection> clientList;
    private LinkedBlockingQueue<Object> messages;
    private ServerSocket serverSocket;

    private GameData gameData;

    public Server() {
        clientList = new ArrayList<>();
        gameData = new GameData();
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
                        Connection runnable = new Connection(serverSocket.accept());
                        Thread t = new Thread(runnable);
                        t.start();
                        clientList.add(runnable);
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
                        if (message instanceof String) {
                            processString((String) message);
                        } else {
                            System.out.println("Unknown class type received: " + message.getClass());
                        }
                        System.out.println("Message received");
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException: " + e.getMessage());
                    }
                }
            }
        };
        messageHandling.setDaemon(true);
        messageHandling.start();

        Timer timer = new Timer("SendTimer", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendToAll(gameData);
            }
        }, 0, 1 / 30);
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
        for (Connection client : clientList) {
            client.write(message);
        }
    }

    private void processString(String command) {
        String[] split = command.split(" ");
        if (split[0].equals("sendchat")) {
            String message = split[1];
            //send this message to all clients
        } else if (split[0].equals("addlobby")) {
            int id = Integer.parseInt(split[1]);
            String name = split[2];
            String host = split[3];
            //new add lobby object                
        } else if (split[0].equals("removelobby")) {
            int id = Integer.parseInt(split[1]);
            //remove lobby object
        } else if (split[0].equals("changelobbyname")) {
            int id = Integer.parseInt(split[1]);
            String name = split[2];
            //change name of the lobby object
        } else if (split[0].equals("joinlobby")) {
            int id = Integer.parseInt(split[1]);
            String player = split[2];
            //join lobby
        } else if (split[0].equals("leavelobby")) {
            int id = Integer.parseInt(split[1]);
            String player = split[2];
            //leave lobby
        } else {
            System.out.println("String not recognized: " + command);
        }
    }
}
