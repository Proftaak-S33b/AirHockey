package networking;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RMI Client class. Connects to a Server.
 * TODO : Use Proxies, save overhead.
 * @author Etienne
 */
public class Client {
     
    // RMI defaults.   
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PROTOCOL = "rmi";
    private static final String DEFAULT_HOSTNAME = "local";
    private static final int DEFAULT_PORT = 1099;
    
    /**
     * The registry as received from the server.
     */
    private Registry registry;
    
    //<editor-fold defaultstate="collapsed" desc="hashmap">
    
    /**
     * Associative array containing all data concerning networking.
     */
    private HashMap data = new HashMap();        
    
    private void sethashmap(){
        data.put("DEFAULT_HOST", "localhost");
        data.put("DEFAULT_PROTOCOL", "rmi");
        data.put("DEFAULT_HOSTNAME", "local");
        data.put("DEFAULT_PORT", 1099);
    }
    //</editor-fold>
    
    /**
     * Initializes a new Client with no setup done: data can be specified later.
     */
    public Client(){
        
    }    
    
    /**
     * Initializes a new Client and immediately connects to a server on the given
     * host and port.
     * @param host a string with the hostname.
     * @param port an int with the portnumber to connect to.
     */
    public Client(String host, int port){
        LocateRegistry(host, port);
    }
    
    /**
     * Locates the registry on the given host and portnumber.
     * @param host a string containing the host. Typically "localhost".
     * @param port an int with the portnumber to connect to.
     */
    public void LocateRegistry(String host, int port){
        try {
            registry = LocateRegistry.getRegistry(host, port);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Does a lookup of an object with the given name in the registry.
     * @param objectname a string with the name of the object you want to look up.
     */
    public void Lookup(String objectname){
        try {
            Interface i = (Interface) registry.lookup(objectname);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    //Old code, i need this to remember the mechanics. pls dont remove ;_;
    public static void main(String[] args){
        
        // Set Security Manager.
        // For RMI to download classes, a security manager must be in force.        

        /*  This will cause errors if you don't have the right policies. disabled.
            Currently disabled.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }        
        */
        
        // The following code can throw a RemoteException.
        try {
            Client c = new Client();
            // Connect to the server's registry.
            Registry registry = LocateRegistry.getRegistry(DEFAULT_HOST, DEFAULT_PORT);
            
            // Get the object from the server's registry.
            Interface i = (Interface) registry.lookup("");
            
            // Print output of the writer to see if everything works.
            System.out.println("Client: "/* + i.interfaceMethod()*/);
            
        // Catches exception by printing error.
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
