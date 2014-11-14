package networking;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

/**
 * <<<<<<< HEAD:Airhockey/src/networking/RMIData.java RMI RMIData class.
 * Connects to a Server.
 *
 * @author Etienne
 */
public class RMIData {

    private Registry registry;

    //<editor-fold defaultstate="collapsed" desc="hashmap">
    /**
     * Associative array containing all data concerning networking.
     */
    private HashMap data = new HashMap();

    private void sethashmap() {
        data.put("DEFAULT_HOST", "localhost");
        data.put("DEFAULT_PROTOCOL", "rmi");
        data.put("DEFAULT_HOSTNAME", "local");
        data.put("DEFAULT_PORT", 1099);
    }
    //</editor-fold>

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
     * Does a lookup of an object with the given name in the registry.
     *
     * @param objectname a string with the name of the object you want to look
     * up.
     */
    public void Lookup(String objectname) {
        try {
            Interface i = (Interface) registry.lookup(objectname);
        } catch (RemoteException | NotBoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //Old code, i need this to remember the mechanics. pls dont remove ;_;
    public static void main(String[] args) {
        // Set Security Manager.
        // For RMI to download classes, a security manager must be in force.        
                /*  This will cause errors if you don't have the right policies. disabled.
         Currently disabled.
         if (System.getSecurityManager() == null) {
         System.setSecurityManager(new SecurityManager());
         }        
         */ // The following code can throw a RemoteException.
        /*
         try {
         RMIData data = new RMIData();
         // Connect to the server's registry.
         Registry registry = LocateRegistry.getRegistry(DEFAULT_HOST, DEFAULT_PORT);

         // Get the object from the server's registry.
         Interface i = (Interface) registry.lookup("");

         // Print output of the writer to see if everything works.
         System.out.println("Client: " + i.interfaceMethod());

         // Catches exception by printing error.
         } catch (RemoteException | NotBoundException e) {
         System.out.println(e.getMessage());
         }
         */
    }

    /**
     * Gets the LobbyData object through RMI
     *
     * @return
     */
    public static LobbyData getLobbyData() {
        //There can be only one!
        return new LobbyData();
    }

    /**
     * Gets the ChatData object through RMI
     *
     * @return
     */
    public static ChatData getChatData() {
        //Params for IP or which ChatData object to retrieve?
        return null;
    }

    /**
     * Gets the GameData object through RMI
     *
     * @param IPAdress The IP-address to connect to
     * @param portNumber The portnumber to use with the IP-address
     * @return The GameData object at this network location, if none found,
     * returns null
     */
    public static GameData getGameData(String IPAdress, int portNumber) {
        return null;
    }
}
