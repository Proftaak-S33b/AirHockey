package z_OLD_RMI;

//<editor-fold defaultstate="collapsed" desc="imports">

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.standalone.rmiDefaults;
import static networking.standalone.rmiDefaults.DEFAULT_PORT;

//</editor-fold>

/**
 * RMI Server class. Connects with Client.
 *
 * @author Etienne & Joris
 */
public class Server {
    
    private InetAddress serverip;
    private Registry registry;
    
    /**
     * Returns the ip-address of the server.
     * @return InetAddress with the ip.      
     */
    public InetAddress getServerIP() {
        return serverip;
    }
    
    /**
     * Gets a complete Key-Value copy of the registry.
     * @return an HashMap (a.k.a Dictionary(C#) / associative array(PHP)) 
     * with the keys (binded objects' names) + values (remote objects themselves).
     * @throws RemoteException thrown when the remotes' shit hits the fan.
     * @throws NotBoundException thrown when the key is not bound in the registry.
     */
    public HashMap getRegistryValues() throws RemoteException, NotBoundException{
        
        HashMap values = new HashMap<>();
        
        // Get all the names registered. (Keys)
        String[] array = registry.list();
        
        // Now put the keys with their remotes in the map.
        for (String s : array) {
            values.put(s, registry.lookup(s));
        }
        
        return values;
    }
    
    /**
     * Creates a new Server with a registry on default port 1099.
     *
     * @throws UnknownHostException thrown when the host isn't found.
     */
    public Server() throws UnknownHostException {
        createRegistry();
        serverip = InetAddress.getLocalHost();
	System.out.println("Started server at " + serverip.toString());
    }

    /**
     * Creates a new Server on a given portnumber.
     *
     * @param port an int with the number of the port to connect to.
     * @throws UnknownHostException thrown when the host isn't found.
     */
    public Server(int port) throws UnknownHostException {
        createRegistry(port);
        serverip = InetAddress.getLocalHost();
	System.out.println("Started server at " + serverip.toString());
    }

    /**
     * Creates a new Registry on default port 1099.
     */
    private void createRegistry() {
        try {
            registry = LocateRegistry.createRegistry(rmiDefaults.DEFAULT_PORT());
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    /**
     * Creates a new Registry on a given portnumber.
     *
     * @param portnumber an int with the number of the port a Client needs to
     * connect to.
     */
    private void createRegistry(int portnumber) {
        try {
            registry = LocateRegistry.createRegistry(portnumber);
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    /**
     * Binds a remote object to the registry. Using "HockeyGame" as name.
     *
     * @param obj a IGameData object to bind to the registry.
     * @throws java.rmi.AlreadyBoundException this exception is thrown when the
     * name is already in use.
     */
    public void bindToRegistry(ILobby obj) throws AlreadyBoundException {
        try {
            registry.bind("hockeygame", (Remote) obj);
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //Old code, i need this to remember the mechanics. pls dont remove ;_;
    //<editor-fold defaultstate="collapsed" desc="main">
    
    /**
     * Include a main() method so we can launch the server and client
     * seperately.
     *
     * @param args
     */
    public static void main(String[] args){
        
        try { 
            
            // Set Security Manager.
            // For RMI to download classes, a security manager must be in force.
            /*  This will cause errors if you don't have the right policies. 
             Currently disabled.
             if (System.getSecurityManager() == null) {
             System.setSecurityManager(new SecurityManager());
             }            
             */
            // Create the registry. Default port: 1099.
            // By binding it like this you don't need to use your command line.
            Registry registry = LocateRegistry.createRegistry(DEFAULT_PORT());
	    Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
	    System.out.println("Started server at " + address.toString());
            registry.bind("registry", registry);
	    // Instantiate the implementation class.
            // Interface is deprecated.
            //Interface i = new InterfaceImpl();
            // bind it to the registry.
            //registry.bind("write", i);
            
        // Catches exception by printing error.
        } catch (RemoteException e) { 
            System.out.println(e.getMessage());
        } catch (UnknownHostException | AlreadyBoundException ex) {
	    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	}        
    }
    
    //</editor-fold>
}
