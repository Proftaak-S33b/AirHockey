/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import networking.GameData;
import networking.sockets.SocketCoordinate.Name;

/**
 *
 * @author Joris
 */
public class Server {

    private ArrayList<Server.ConnectionToClient> clientList;
    private LinkedBlockingQueue<Object> messages;
    private ServerSocket serverSocket;
    private GameData gameData;

    public Server() {
        clientList = new ArrayList<>();
        messages = new LinkedBlockingQueue<>();
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
                        if (message instanceof Message) {
                            sendToAll(message);
                        } else if (message instanceof SocketCoordinate) {
                            processMessage((SocketCoordinate) message);
                        } else if (message instanceof String) {
                            processString((String) message);
                        } else {
                            System.out.println("Unknown class type received");
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
        for (ConnectionToClient client : clientList) {
            client.write(message);
        }
    }

    private void processMessage(SocketCoordinate message) {
        switch (message.name) {
            case PUCK_POS:
                gameData.setPuckPos(message.x, message.y);
                break;
            case PUCK_VEL:
                gameData.setPuckVelocity(message.x, message.y);
                break;
            case RED:
                gameData.setRedPodPos(message.x, message.y);
                break;
            case BLUE:
                gameData.setBluePodPos(message.x, message.y);
                break;
            case GREEN:
                gameData.setGreenPodPos(message.x, message.y);
                break;
        }
    }

    private void processString(String message) {
        String key = message.substring(0, 2);
        String value = message.substring(2);
        if (key.equals("P1:")) {
            gameData.setScoreP1(Integer.parseInt(value));
        } else if (key.equals("P2:")) {
            gameData.setScoreP2(Integer.parseInt(value));
        } else if (key.equals("P3:")) {
            gameData.setScoreP3(Integer.parseInt(value));
        } else if (key.equals("Ro:")) {
            gameData.setRound(Integer.parseInt(value));
        } else {
            System.out.println("String not recognized: " + message);
        }
    }
    
    public void setScoresAndRound(int score1, int score2, int score3, int round){
        gameData.setScoreP1(score1);
        gameData.setScoreP2(score2);
        gameData.setScoreP3(score3);
        gameData.setRound(round);
    }
}
