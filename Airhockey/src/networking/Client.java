package networking;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI Client class. Connects to a Server.
 * @author Etienne
 */
public class Client {
        
    // RMI defaults.   
    // TODO move to hashmap
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PROTOCOL = "rmi";
    private static final String DEFAULT_HOSTNAME = "local";
    private static final int DEFAULT_PORT = 1099;
    
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
