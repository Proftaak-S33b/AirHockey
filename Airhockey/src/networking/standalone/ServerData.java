package networking.standalone;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import networking.IPlayer;

/**
 *
 * @author Etienne
 */
public class ServerData extends UnicastRemoteObject implements IServerData {

    /**
     * List of all the clients.
     */
    public List<ClientData> clients;

    public ServerData() throws RemoteException {
        clients = new ArrayList<>();
    }

    /**
     * Registers a client with the server.
     */
    @Override
    public boolean add(
            InetAddress address, String name, String description,
            IPlayer host, Socket socket, ServerSocket serversocket
    ) throws RemoteException {
        ClientData client = new ClientData(address, name, description, host, socket, serversocket);
        System.out.println("Registering client with name: " + client.getName());
        return clients.add(client);
    }

    /**
     * Unregisters a client with the server.
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean remove(ClientData client) throws RemoteException {
        System.out.println("Unregistering client with name: " + client.getName());
        return clients.remove(client);
    }

    /**
     * 
     * @return
     * @throws RemoteException 
     */
    @Override
    public List<ClientData> getClients() throws RemoteException {
        return Collections.unmodifiableList(clients);
    }
}