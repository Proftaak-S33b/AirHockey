package networking.sockets;

import java.io.*;
import java.net.*;

/**
 * Serverside socket implementation for pushing model.
 * @author Etienne
 */
public class SocketServer {
        public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(1099);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1099.");
            System.exit(-1);
        }

        while (listening) {
            // create new Thread which runs Multiserverrunnable
            Thread t = new Thread(new SocketRunnable(serverSocket.accept()));
            // start Thread
            t.start();
        }
        serverSocket.close();
    }
}
