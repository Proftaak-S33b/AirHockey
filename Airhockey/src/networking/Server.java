package networking;

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

/**
 * RMI Server class. Connects with Client.
 * @author Etienne
 */
public class Server {
        
    // RMI defaults.    
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PROTOCOL = "rmi";
    private static final String DEFAULT_HOSTNAME = "local";
    private static final int DEFAULT_PORT = 1099;    
    
    private InetAddress serverip;
    private Registry registry;
        
    /**
     * Returns the ip-address of the server.
     * @return InetAddress with the ip. 
     * Can be converted to a String using toString().
     */
    public InetAddress getServerIP(){
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
        
        HashMap values = new HashMap<String, Remote>();
        
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
     * @throws UnknownHostException thrown when the host isn't found.
     */
    public Server() throws UnknownHostException{
        CreateRegistry();
        serverip = InetAddress.getLocalHost();
    }
    
    /**
     * Creates a new Server on a given portnumber.
     * @param port an int with the number of the port to connect to.
     * @throws UnknownHostException thrown when the host isn't found.
     */
    public Server(int port) throws UnknownHostException{
        CreateRegistry(port);
        serverip = InetAddress.getLocalHost();
    }
    
    /**
     * Creates a new Registry on default port 1099.
     */
    public void CreateRegistry(){
        try {
            registry = LocateRegistry.createRegistry(DEFAULT_PORT);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates a new Registry on a given portnumber.
     * @param portnumber an int with the number of the port a Client needs to connect to.
     */
    public void CreateRegistry(int portnumber){
        try {
            registry = LocateRegistry.createRegistry(portnumber);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Binds a remote object to the registry.
     * @param name a String with the name to bind to.
     * @param obj a remote object to bind to the registry.
     */
    public void BindToRegistry(String name, Remote obj){
        try {
            registry.bind(name, obj);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            //Do you want to override the bind instead?
            //registry.rebind(name, obj);
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
 ////////////////////////////////////////////////////////////////////////////
    
    //Old code, i need this to remember the mechanics. pls dont remove ;_;
    //<editor-fold defaultstate="collapsed" desc="main">
    /**
     * Include a main() method so we can launch the server and client seperately.
     * @param args 
     */
    public static void main(String[] args){
        
        // The following code can throw a RemoteException.
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
            Registry registry = LocateRegistry.createRegistry(DEFAULT_PORT);

            // Instantiate the implementation class.
            // Interface is deprecated.
            //Interface i = new InterfaceImpl();
            
            // bind it to the registry.
            //registry.bind("write", i);
            
        // Catches exception by printing error.
        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }        
    }  
    //</editor-fold>
}