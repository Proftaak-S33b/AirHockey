/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import networking.commands.ServerReceiver;

/**
 *
 * @author Joris
 */
public class Server {

    private final ArrayList<Connection> clientList;
    private ServerSocket serverSocket;
    private ServerReceiver serverReceiver;
    private boolean running = true;

    public Server() {
        clientList = new ArrayList<>();
        serverReceiver = new ServerReceiver();
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Started server at: " + InetAddress.getLocalHost().getHostAddress() + ":4444");
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }

        Thread accept = new Thread() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Connection connection = new Connection(serverSocket.accept(), serverReceiver.getQueue());
                        System.out.println(connection.getSocket().getInetAddress() + " connected.");
                        synchronized (clientList) {
                            clientList.add(connection);
                            serverReceiver.addConnection(connection);
                        }
                    } catch (IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                    }
                }
            }
        };
        accept.start();
    }
}
