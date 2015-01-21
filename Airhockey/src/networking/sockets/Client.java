package networking.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that handles the client connection
 *
 * @author Jur
 */
public class Client implements Runnable {

    private final boolean isHost;
    private Socket socket;
    private String buffer = "";
    private ArrayList<ClientData> clients = null;
    private ClientData me = null;

    /**
     * Creates a new client connection
     *
     * @param isHost If the client is also the host or not.
     * @param socket The socket to which the client is connected to.
     */
    public Client(boolean isHost, Socket socket) {
        this.isHost = isHost;
        this.socket = socket;
    }

    @Override
    public void run() {
        //Listen for messages
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
            //Receive latest client data
            clients = (ArrayList<ClientData>) inObject.readObject();
            //Receive own client object
            me = (ClientData) inObject.readObject();
            //Listen for messages and sent new ones
            while (!out.checkError()) {
                //Check if receiving data
                if (in.ready()) {
                    if (!(buffer = in.readLine()).equals("")) {
                        if (buffer.equals("4444-CLIENTDATA")) {
                            clients = (ArrayList<ClientData>) inObject.readObject();
                        } else if (buffer.equals("4444-GAMEDATA")) {
                            Data.gameData = (GameData) inObject.readObject();
                        }
                    }
                }
                //Sent check if socket is still alive
                out.println("");

                //Let thread sleep for a while
                Thread.sleep(10);
            }
            out.close();
            in.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("SYSTEM: Something went wrong!");
            try {
                socket.close();
            } catch (Exception ex) {

            }
        }

        //START HOST MIGRATION
        //TODO
        if (!isHost) {
            System.out.println("SYSTEM: Connection lost! Starting host migration");
            int i = 0;
            for (ClientData client : clients) {
                if (i == 0) {
                    //Skip first client, as this is the first host
                } else {
                    if (client == me) {
                        Thread t = new Thread(new Host());
                        t.start();
                        try {
                            Socket socket = new Socket("127.0.0.1", 4444);
                            t = new Thread(new Client(true, socket));
                            t.start();
                        } catch (IOException ex) {
                        }
                    } else {
                        System.out.println("SYSTEM: Wait for new host to start...");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                        }
                        try {
                            Socket socket = new Socket(client.getIpadress(), 4444);
                            Thread t = new Thread(new Client(false, socket));
                            t.start();
                            System.out.println("SYSTEM: Connected to new host");
                            break;
                        } catch (IOException ex) {
                            System.out.println("SYSTEM: Connection to new host failed!");
                        }
                    }
                }
                //Goto next step
                i++;
            }
        }
    }

}
