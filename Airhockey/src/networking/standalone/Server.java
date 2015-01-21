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
import networking.commands.Command;

/**
 *
 * @author Joris
 */
public class Server {

    private ArrayList<Connection> clientList;
    private LinkedBlockingQueue<Object> serverCommands;
    private ServerSocket serverSocket;

    public Server() {
        clientList = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Started server at: " + InetAddress.getLocalHost().getHostAddress() + ":4444");
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }

        Thread accept = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Connection connection = new Connection(serverSocket.accept());
                        clientList.add(connection);
                    } catch (IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                    }
                }
            }
        };
        accept.start();
    }
}
