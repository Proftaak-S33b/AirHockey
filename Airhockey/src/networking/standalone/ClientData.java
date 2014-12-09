/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.net.*;
import networking.IPlayer;

/**
 * Data model class for retaining client data.
 * Used when a client has registered with a server.
 * <hr>
 * When a client connects to a dedicated server, the server will register the 
 * client in a list of clients. 
 * The client-specific data is stored in this Client object.
 * This way, the server can have a lightweight list of clients
 * to index and search
 * while still retaining all the associated data for optimized searching.
 * 
 * tl;dr 
 * 'muh enterprise'.
 * @author Etienne
 */
public class ClientData implements IClientData{
   
   // Properties - TODO: Are all these needed? redundancies? Who does the setup?
   
   /** ipaddress of the server */
   private final InetAddress address;
   
   /** name for the server to memorize easier */
   private final String name;
   
   /** title of the lobby */
   private final String description;
   
   /** socket of the clientclient */
   private final Socket socket;
   
   /** socket of the clientserver */
   private final ServerSocket serversocket;
   
   /** player who tries to go online */
   private final IPlayer host;

    /**
     * ipaddress of the server
     * @return the address
     */
    public InetAddress getAddress() {
	return address;
    }

    /**
     * name for the server to memorize easier
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * title of the lobby
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * socket of the clientclient
     * @return the socket
     */
    public Socket getSocket() {
	return socket;
    }

    /**
     * socket of the clientserver
     * @return the serversocket
     */
    public ServerSocket getServersocket() {
	return serversocket;
    }

    /**
     * player who tries to go online
     * @return the host
     */
    public IPlayer getHost() {
	return host;
    }
   
   /**
    * Packages all the data neatly and tightly in one object for easy transport.
     * @param address
     * @param name
     * @param description
     * @param host
     * @param socket
     * @param serversocket
    */
   public ClientData(InetAddress address, String name, String description,
	   IPlayer host, Socket socket, ServerSocket serversocket){
       this.address = address;
       this.name = name;
       this.description = description;
       this.socket = socket;
       this.serversocket = serversocket;
       this.host = host;
   }
}
