package networking.standalone;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import networking.IPlayer;

/**
 *
 * @author Etienne u wot m9
 */
public interface IServerData extends Remote {

    /**
     * Registers a client with the server.
     *
     * @param address
     * @param name
     * @param description
     * @param host
     * @param socket
     * @param serversocket
     * @throws java.rmi.RemoteException
     */
    public void registerClient(
	    InetAddress address, String name, String description,
	    IPlayer host, Socket socket, ServerSocket serversocket
    ) throws RemoteException;

    /**
     * Unregisters a client with the server.
     *
     * @param client
     */
    public void unregisterClient(ClientData client) throws RemoteException;

    public HashMap<String, ClientData> getClients() throws RemoteException;

    public HashMap<String, String> k() throws RemoteException;
}
