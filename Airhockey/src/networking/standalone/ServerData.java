package networking.standalone;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import networking.IPlayer;

/**
 *
 * @author Etienne
 */
public class ServerData implements IServerData {

    /**
     * List of all the clients.
     */
    public Map<String, ClientData> clients;
    
    /**
     * Registers a client with the server.
     */
    @Override
    public void registerClient(
	    InetAddress address, String name, String description,
	    IPlayer host, Socket socket, ServerSocket serversocket
    ) throws RemoteException {
	ClientData client = new ClientData(address, name, description, host, socket, serversocket);
	//clients.put(client.getName(), client);
	System.out.println("Registering client under " + client.getName());
    }

    /**
     * Unregisters a client with the server.
     */
    @Override
    public void unregisterClient(ClientData client) throws RemoteException {
	clients.remove(client.getName());
	System.out.println("Unregistering client with key " + client.getName());
    }

    @Override
    public HashMap<String, ClientData> getClients() throws RemoteException {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, String> k() throws RemoteException {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
