package networking.standalone;

//<editor-fold defaultstate="collapsed" desc="imports">

import game.Human;
import java.net.*;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.IPlayer;
import networking.LobbyData;

//</editor-fold>

/*

Note to self

make the client request a list of hosts from this standalone.
make this standalone keep track of all clients hosting a game.
make sure other servers unregister at *some* point.

*/

/**
 * Standalone RMI Server for dedicated server hosting.
 * Serves as a networking interface for individual clients.
 * http://stackoverflow.com/questions/4637362/communication-via-internet-in-java
 * @author Etienne
 */
public class rmiStandaloneServer{
    
    /**
     * The ipv4 address of the server. 
     * This is the ip-address the clients will attempt to connect to.
     */
    private final Inet4Address address;
    
    /**
     * In case of multiple dedicated servers, you might want to give each server 
     * its own name so you can identify them more easily.
     * e.g. EUROPE, NORTHAMERICA, ASIA for servers in their respective regions.
     */
    private final String name;
    
    /**
     * A list of client applications registered to the server.
     */
    private Map<String, ClientData> clients;
    
    /**
     * To make all our data accessible we use this registry.
     */
    public Registry registry;
    
    /**
     * Initializes the server. 
     * Name is optional.
     * @param name an optional string with the name of the server.
     */
    public rmiStandaloneServer(String name) throws UnknownHostException{
	
	// Info about this server.
	    this.name = name;
	    this.address = (Inet4Address) Inet4Address.getLocalHost();
	    
	// Initialize list of clients and register them.
	    this.clients = new HashMap();
	try {
	    registry = LocateRegistry.createRegistry(rmiDefaults.DEFAULT_PORT);
	    TestData td = new TestData();
	    td.setString("Connection established.");
	    registry.bind("testdata", (Remote) td);
	    printInfo();
	} catch (RemoteException | AlreadyBoundException ex) {
	    Logger.getLogger(rmiStandaloneServer.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    /**
     * Initializes the server. 
     * Name is optional.
     */
    public rmiStandaloneServer() throws UnknownHostException{
	
	// Info about this server.
	this.name = "Dedicated Airhockey Server";
	this.address = (Inet4Address) Inet4Address.getLocalHost();
	
	// Initialize list of clients and register them.
	this.clients = new HashMap();
	try {
	    registry = LocateRegistry.createRegistry(rmiDefaults.DEFAULT_PORT);
	    //registry.bind("clients", (Remote) clients);
	    printInfo();
	} catch (RemoteException /*| AlreadyBoundException*/ ex) {
	    Logger.getLogger(rmiStandaloneServer.class.getName()).log(Level.SEVERE, null, ex);
	}
	printInfo();
    }
    
    /**
     * Returns the complete set of data for one client.
     *
     * @param client the specified client of which the data is requested.
     */
    public ClientData getClientData(String name) {
	return (ClientData) clients.get(name);
    }

    /**
     * Returns the ipv4 address of the server.
     *
     * @return an InetAddress containing the address of the server.
     */
    public InetAddress getAddress() {
	return this.address;
    }

    /**
     * Returns the list with clients.
     */
    public Map getList(){
	return this.clients;
    }
  
    /**
     * Returns the name of the server.
     * @return a String with the name of the server.
     */
    public String getName(){
	return this.name;
    }
    
    /**
     * Registers a client with the server.
     */
    public void registerClient(
	    InetAddress address, String name, String description,
	    IPlayer host, Socket socket, ServerSocket serversocket
    ){
	ClientData client = new ClientData(address, name, description, host, socket, serversocket);
	clients.put(client.getName(), client);
	System.out.println("Registering client under " + client.getName());
    }
    
    /**
     * Unregisters a client with the server.
     */
    public void unregisterClient(ClientData client){
	clients.remove(client.getName());
	System.out.println("Unregistering client with key " + client.getName());
    }
    
    /**
     * Does a complete writeout of every registered client and its data.
     */
    public void printInfo(){
	System.out.println(
	    "Standalone server " + this.name + " at "
	    + this.address.toString() + ", with a clientlist the size of "
	    + this.clients.size() + "."
	);
    }
    
    /**
     * Refreshes the list of registered clients.
     */
    public void refresh(){
	System.out.println("Refreshing list...");
	// refresh list
	System.out.println("Refreshed!");
    }
    
    /**
     * Main method for launching the server out-of-the-box.
     * @param args 
     */
    public static void main(String[] args){
	System.out.println("Starting standalone server..");
	
	try {
	    rmiStandaloneServer rmi = new rmiStandaloneServer("NetworkServer");
	} catch (UnknownHostException ex) {
	    Logger.getLogger(rmiStandaloneServer.class.getName()).log(Level.SEVERE, null, ex);
	    System.out.println("Failed. Exiting program.");
	}
	
	System.out.println("Running server..");
	
	while(true){
	    // Keep the server running.
	}	
    }
}
