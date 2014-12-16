package networking.standalone;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
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
     * @return 
     * @throws java.rmi.RemoteException
     */
    public boolean add(
	    InetAddress address, String name, String description,
	    IPlayer host, Socket socket, ServerSocket serversocket
    ) throws RemoteException;

    /**
     * Unregisters a client with the server.
     *
     * @param client
     * @return 
     * @throws java.rmi.RemoteException
     */
    public boolean remove(ClientData client) throws RemoteException;

    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public List<ClientData> getClients() throws RemoteException;
}
