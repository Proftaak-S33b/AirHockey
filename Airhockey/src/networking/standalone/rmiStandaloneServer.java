package networking.standalone;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.net.*;
//</editor-fold>

/*

 Note to self

 make the client request a list of hosts from this standalone.
 make this standalone keep track of all clients hosting a game.
 make sure other servers unregister at *some* point.

 */
/**
 * Standalone RMI Server for dedicated server hosting. Serves as a networking
 * interface for individual clients.
 * http://stackoverflow.com/questions/4637362/communication-via-internet-in-java
 *
 * @author <u>Etienne</u> -> Reworked by Joris
 */
public class rmiStandaloneServer {

    /**
     * The ipv4 address of the server. This is the ip-address the clients will
     * attempt to connect to.
     */
    private final Inet4Address address;
    
    private Server server;

    /**
     * Initializes the server.
     *
     * @throws java.net.UnknownHostException
     */
    public rmiStandaloneServer() throws UnknownHostException {
        // Info about this server.
        this.address = (Inet4Address) Inet4Address.getLocalHost();
        this.server = new Server();
    }

    /**
     * Main method for launching the server out-of-the-box.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting standalone server..");

        try {
            rmiStandaloneServer rmi = new rmiStandaloneServer();
            System.out.println("Running server at " + Inet4Address.getLocalHost());
        } catch (UnknownHostException ex) {
            System.out.println("Failed. Exiting program.");
            System.exit(-1);
        }
    }
}
