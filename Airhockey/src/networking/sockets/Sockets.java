/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joris
 */
public class Sockets {

    /**
     *
     * @param address leave null if you are host, enter IP if you want to be
     * client
     * @param host
     */
    public Sockets(InetAddress address, int host) {
        if (address == null) {
            Server server = new Server();

            try {
                Thread.sleep(50);
                Client client = new Client("localhost", 4444, null);
            } catch (IOException | InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            try {
                Client client = new Client("localhost", 4444, null);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
