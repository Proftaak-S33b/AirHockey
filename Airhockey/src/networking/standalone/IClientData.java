/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import networking.IPlayer;

/**
 * RMI-related interface for ClientData wrapper object.
 * @author Etienne
 */
public interface IClientData {
    
    /**
     * ipaddress of the server
     * @return the address
     */
    public InetAddress getAddress();

    /**
     * name for the server to memorize easier
     * @return the name
     */
    public String getName();

    /**
     * title of the lobby
     * @return the description
     */
    public String getDescription();

    /**
     * socket of the clientclient
     * @return the socket
     */
    public Socket getSocket();

    /**
     * socket of the clientserver
     * @return the serversocket
     */
    public ServerSocket getServersocket();

    /**
     * player who tries to go online
     * @return the host
     */
    public IPlayer getHost();
}
