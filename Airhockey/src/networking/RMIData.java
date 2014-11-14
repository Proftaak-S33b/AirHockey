package networking;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI RMIData class. Connects to a Server.
 *
 * @author Etienne
 */
public class RMIData {

    // RMI defaults.   
    // TODO move to hashmap
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PROTOCOL = "rmi";
    private static final String DEFAULT_HOSTNAME = "local";
    private static final int DEFAULT_PORT = 1099;

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
     * @return The GameData object at this network location, if none found, returns null
     */
    public static GameData getGameData(String IPAdress, int portNumber) {
        return null;
    }

    public static void main(String[] args) {

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
