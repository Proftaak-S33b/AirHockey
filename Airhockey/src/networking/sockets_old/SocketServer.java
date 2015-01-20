package networking.sockets_old;

import java.io.*;
import java.net.*;

/**
 * Serverside socket implementation for pushing model.
 *
 * @author Etienne
 */
public class SocketServer {
    
    public SocketServer(InetAddress address, int host){
        if (address == null) {
            System.out.println("SYSTEM: Starting new host...");
            Thread t = new Thread(new Host());
            t.start();
            Socket socket;
            try {
                socket = new Socket("localhost", 4444);
                t = new Thread(new Client(true, socket));
                t.start();
            } catch (IOException ex) {
                System.out.println("SYSTEM: Connection to own pc failed...!");
            }
        } else {
            System.out.println("SYSTEM: Connecting to host...");
            Socket socket;
            try {
                socket = new Socket(address, 4444);
                Thread t = new Thread(new Client(false, socket));
                t.start();
                System.out.println("SYSTEM: Connected!");
            } catch (IOException ex) {
                System.out.println("Connection to host failed!");
            }
        }
    }
}
