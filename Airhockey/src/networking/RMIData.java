package networking;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Waarom rename? Dit is de client toch? En waarom die static methods?
// (Als je een client nodig hebt voor je lobbymanager, maak er dan daar een?)



/**
 * RMI RMIData class. Connects to a Server.
 * Interesting read / todo? :
 * http://www.javaworld.com/article/2076234/soa/get-smart-with-proxies-and-rmi.html 
 * @author Etienne
 */
public class RMIData {

    private Registry registry;

    /**
     * Initializes a new Client with no setup done: data can be specified later.
     */
    public RMIData() {

    }

    /**
     * Initializes a new Client and immediately connects to a server on the
     * given host and port.
     *
     * @param host a string with the hostname.
     * @param port an int with the portnumber to connect to.
     */
    public RMIData(String host, int port) {
        LocateRegistry(host, port);
    }
    
    /**
     * Locates the registry on the given host and portnumber.
     *
     * @param host a string containing the host. Typically "localhost".
     * @param port an int with the portnumber to connect to.
     */
    public void LocateRegistry(String host, int port) {
        try {
            registry = LocateRegistry.getRegistry(host, port);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Does a lookup for an object under the given name in the registry.
     *
     * @param key a string with the name of the object key you want to 
     * look up.
     * @return a Remote object or null if not found.
     */
    public Remote Lookup(String key) {
        try {
            
            Remote remoteobject = registry.lookup(key);
            return remoteobject;
            
        } catch (RemoteException | NotBoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
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
        return (ChatData) Lookup("chatdata");
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
        return (GameData) Lookup("gamedata");
    }
}
