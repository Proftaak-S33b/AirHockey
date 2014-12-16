package networking.standalone;

import java.io.Serializable;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
public class ClientData extends UnicastRemoteObject implements IClientData{
   
    // RSVP/TODO: Are all these needed? redundancies? Who does the setup?
    
    /**
     * ipaddress of the server
     */
    private final InetAddress address;

    /**
     * name for the server to memorize easier
     */
    private final String name;

    /**
     * title of the lobby
     */
    private final String description;

    /**
     * socket of the clientclient
     */
    private final Socket socket;

    /**
     * socket of the clientserver
     */
    private final ServerSocket serversocket;

    /**
     * player who tries to go online
     */
    private final IPlayer host;
    
    /**
     * 
     */
    private int playerCount;

    /**
     * ipaddress of the server
     *
     * @return the address
     */
    @Override
    public InetAddress getAddress()  throws RemoteException{
	return address;
    }

    /**
     * name for the server to memorize easier
     *
     * @return the name
     */
    @Override
    public String getName()  throws RemoteException{
	return name;
    }

    /**
     * title of the lobby
     *
     * @return the description
     */
    @Override
    public String getDescription()  throws RemoteException{
	return description;
    }

    /**
     * socket of the clientclient
     *
     * @return the socket
     */
    @Override
    public Socket getSocket() throws RemoteException {
	return socket;
    }

    /**
     * socket of the clientserver
     *
     * @return the serversocket
     */
    @Override
    public ServerSocket getServersocket() throws RemoteException {
	return serversocket;
    }

    /**
     * player who tries to go online
     *
     * @return the host
     */
    @Override
    public IPlayer getHost() throws RemoteException {
	return host;
    }

    /**
     * Packages all the data neatly and tightly in one object for easy
     * transport.
     *
     * @param address
     * @param name
     * @param description
     * @param host
     * @param socket
     * @param serversocket
     * @throws java.rmi.RemoteException
     */
    public ClientData(InetAddress address, String name, String description,
	    IPlayer host, Socket socket, ServerSocket serversocket) throws RemoteException{
	this.address = address;
	this.name = name;
	this.description = description;
	this.socket = socket;
	this.serversocket = serversocket;
	this.host = host;
        this.playerCount = 1;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getRanking()  throws RemoteException{
        return host.getRanking();
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getPlayerAmount()  throws RemoteException{
        return playerCount;
    }
    
    /**
     * Add one player to the lobby count
     */
    @Override
    public void increasePlayerAmount()  throws RemoteException{
        playerCount++;
    }
    
    /**
     * Remove one player from the lobby count
     */
    @Override
    public void decreasePlayerAmount()  throws RemoteException{
        playerCount--;
    }
}
