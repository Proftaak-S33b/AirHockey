package networking;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI Server class. Connects with Client.
 * @author Etienne
 */
public class Server {
        
    // RMI defaults.    
    // TODO move to hashmap
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PROTOCOL = "rmi";
    private static final String DEFAULT_HOSTNAME = "local";
    private static final int DEFAULT_PORT = 1099;    
    
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
