package networking.standalone;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
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

    /**
     *
     */
    private IServerData data;

    private ArrayList<ConnectionToClient> clientList;
    private LinkedBlockingQueue<String> messages;
    private ServerSocket serverSocket;

    /**
     * To make all our data accessible we use this registry.
     */
    public Registry registry;

    /**
     * Initializes the server.
     *
     * @throws java.net.UnknownHostException
     */
    public rmiStandaloneServer() throws UnknownHostException {
        // Info about this server.
        this.address = (Inet4Address) Inet4Address.getLocalHost();

        try {
            // Initialize ServerData object
            data = new ServerData();
            registry = LocateRegistry.createRegistry(rmiDefaults.DEFAULT_PORT());
            registry.bind("serverdata", data);
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        } catch (AlreadyBoundException ex) {
            System.out.println("AlreadyBoundException: " + ex.getMessage());
        }

        clientList = new ArrayList<>();
        messages = new LinkedBlockingQueue<>();
        try {
            serverSocket = new ServerSocket(69);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }

        Thread accept = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket s = serverSocket.accept();
                        clientList.add(new ConnectionToClient(s));
                    } catch (IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                    }
                }
            }
        };
        accept.setDaemon(true);
        accept.start();

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = messages.take();
                        sendToAll(message);
                        System.out.println("Message: " + message);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException: " + e.getMessage());
                    }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    private class ConnectionToClient {

        BufferedReader in;
        PrintWriter out;
        Socket socket;

        ConnectionToClient(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            Thread read = new Thread() {
                @Override
                public void run() {
                    try {
                        String message = in.readLine();
                        while (message != null) {
                            messages.put(message);
                            message = in.readLine();
                        }
                    } catch (IOException | InterruptedException ex) {
                        System.out.println("CTC Error:" + ex.getMessage());
                    }
                }
            };

            read.setDaemon(true); // terminate when main ends
            read.start();
        }

        public void write(String message) {
            out.println(message);
            out.flush();
        }
    }

    public void sendToAll(String message) {
        for (ConnectionToClient client : clientList) {
            client.write(message);
        }
    }

    /**
     * Returns the ipv4 address of the server.
     *
     * @return an InetAddress containing the address of the server.
     */
    public InetAddress getAddress() {
        return this.address;
    }

    /**
     * Does a complete writeout of every registered client and its data.
     */
    public void printInfo() {
        try {
            final List<ClientData> clients = this.data.getClients();
            System.out.println("Standalone server at "
                    + this.address.toString() + ", with a clientlist the size of "
                    + clients.size() + ".");
            for (ClientData d : clients) {
                System.out.println(d.toString());
            }
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    /**
     *
     */
    public void exit() {
        try {
            registry.unbind("serverdata");
            UnicastRemoteObject.unexportObject(data, true);
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("ERROR: 'serverdata' name not bound in registry.");
        }
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
