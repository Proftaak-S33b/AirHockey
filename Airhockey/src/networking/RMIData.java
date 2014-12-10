package networking;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Waarom rename? Dit is de client toch? En waarom die static methods?
// (Als je een client nodig hebt voor je lobbymanager, maak er dan daar een?)
/**
 * RMI RMIData class. Connects to a Server. Interesting read / todo? :
 * http://www.javaworld.com/article/2076234/soa/get-smart-with-proxies-and-rmi.html
 *
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
}
