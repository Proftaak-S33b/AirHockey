package networking;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    
    private Registry registry;
    
    /**
     * Creates a new Server on default port 1099.
     */
    public Server(){
        CreateRegistry();
    }
    
    /**
     * Creates a new Server on a given portnumber.
     * @param port an int with the number of the port to connect to.
     */
    public Server(int port){
        CreateRegistry(port);
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
            Interface i = new InterfaceImpl();
            
            // bind it to the registry.
            registry.bind("write", i);
            
        // Catches exception by printing error.
        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }        
    }  
}
