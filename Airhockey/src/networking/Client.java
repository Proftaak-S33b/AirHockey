package networking;

//<editor-fold defaultstate="collapsed" desc="imports">
import game.Human;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.standalone.ClientData;
import networking.standalone.IServerData;
import networking.standalone.TestData;
import static networking.standalone.rmiDefaults.DEFAULT_PORT;
import static networking.standalone.rmiDefaults.DEFAULT_SERVER_IP;

//</editor-fold>
/**
 * RMI Client class. Connects to a Server.
 *
 * @author Etienne
 */
public class Client {

    /**
     * The registry to link to the Server.
     */
    private Registry registry;

    /**
     * Initializes a new Client with no setup done: data can be specified later.
     */
    public Client() {

    }

    /**
     * Initializes a new Client and immediately connects to a server on the
     * given host and port.
     *
     * @param host a string with the hostname.
     * @param port an int with the portnumber to connect to.
     */
    public Client(String host, int port) {
        locateRegistry(host, port);
    }

    /**
     * Locates the registry on the given host and portnumber.
     *
     * @param host a string containing the host. Typically "localhost".
     * @param port an int with the portnumber to connect to.
     */
    public void locateRegistry(String host, int port) {
        try {
            registry = LocateRegistry.getRegistry(host, port);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Does a lookup for an object under the given name in the registry.
     *
     * @param key a string with the name of the object key you want to look up.
     * @return a Remote object or null if not found.
     */
    public Remote lookup(String key) {
        try {
            Remote remoteobject = registry.lookup(key);
            return remoteobject;
        } catch (RemoteException | NotBoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Relocates to a different server. If port is unknown, use zero: this will
     * default to the default port. If host is null, uses localhost.
     *
     * @param host A String with the hostname.
     * @param port an int with the portnumber.
     */
    public void relocate(String host, int port) {
        port = (port == 0) ? 1099 : port;

        if (registry != null) {
            registry = null;
        }

        locateRegistry(host, port);
    }

    /**
     * Gets the LobbyData object through RMI
     *
     * @return
     */
    public /*static*/ ILobbyData getLobbyData() {
        //There can be only one!
        return new LobbyData();
    }

    /**
     * Gets the ChatData object through RMI
     *
     * @return
     */
    public /*static*/ ChatData getChatData() {
        //Params for IP or which ChatData object to retrieve?
        //nah, registry is initialized, no need to reconnect.
        //if we want different objects, make more client/rmidata objects?
        return (ChatData) lookup("chatdata");
    }

    /**
     * Gets the GameData object through RMI
     *
     * @param IPAddress The IP-address to connect to
     * @param portNumber The portnumber to use with the IP-address
     * @return The GameData object at this network location, if none found,
     * returns null
     */
    public /*static*/ GameData getGameData(String IPAddress, int portNumber) {
        return (GameData) lookup("gamedata");
    }

    /**
     * Runs the client seperately.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Initialize Client.
        Client c = new Client();

        // Connect to central RMI server.
        c.locateRegistry("145.93.89.162", DEFAULT_PORT);

        try {
            IServerData data = (IServerData) c.lookup("serverdata");
            data.add(InetAddress.getLocalHost(), "Hoi", "Meer hoi", new Human("Hans", "test", 20), null, null);
            final List<ClientData> clients = data.getClients();
            System.out.println("Amount of clients found: " + clients.size());
            for (ClientData d : clients) {
                System.out.println("Client: " + d.getDescription() + " at " + d.getAddress().toString());
            }

        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        } catch (UnknownHostException ex) {
            System.out.println("UnknownHostException: " + ex.getMessage());
        }
    }
}
