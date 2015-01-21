/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Jur
 */
public class Host implements Runnable {

    public Host() {

    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = null;
            boolean listening = true;

            try {
                serverSocket = new ServerSocket(4444);
                Socket socket = new Socket("127.0.0.1", 4444);
                Thread t = new Thread (new Client(true, socket));
                t.start();
            } catch (IOException e) {
                System.err.println("SYSTEM: Could not listen on port: 4444.");
                System.exit(-1);
            }

            System.out.println("SYSTEM: Listening for clients...");
            while (listening) {
                // Listen for clients and start new thread
                Thread t = new Thread(new ConnectionRunnable(serverSocket.accept()));
                System.out.println("SYSTEM: Client connected!");
                t.start();
            }
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("SYSTEM: Something went wrong!");
            System.exit(-1);
        }
    }
}
