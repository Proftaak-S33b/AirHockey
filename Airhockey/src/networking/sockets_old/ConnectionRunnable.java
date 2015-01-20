package networking.sockets_old;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that handles all the hosts connections
 *
 * @author Jur
 */
public class ConnectionRunnable implements Runnable {

    private final Socket socket;
    private String lastReceived;
    private String buffer;
    private final ClientData client;
    private ArrayList<ClientData> lastSentClientList = null;

    public ConnectionRunnable(Socket socket) {
        this.socket = socket;
        client = new ClientData(socket.getInetAddress().toString().substring(1));
        Data.clients.add(client);
        lastReceived = "";
        buffer = "";
    }

    @Override
    public void run() {
        try {
            System.out.println("Client ip: " + socket.getInetAddress());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
            //Send latest client list to client
            outObject.writeObject(Data.clients);
            lastSentClientList = Data.clients;
            //Send client own data
            outObject.writeObject(client);
            //Listen for messages and sent new ones
            while (!out.checkError()) {
                if (in.ready()) {
                    if (!(buffer = in.readLine()).equals("")) {
                        lastReceived = buffer;
                        Data.LastMessage = lastReceived;
                    }
                }
                if (!lastReceived.equals(Data.LastMessage)) {
                    lastReceived = Data.LastMessage;
                    out.println(Data.LastMessage);
                }
                //Let the thread sleep for a while
                Thread.sleep(25);
                //Send heartbeat
                out.println("");
                
                //Check if last sent client list is up to date, else sent it again
                if(lastSentClientList != Data.clients){
                    out.println("OBJECTINCOMING4444");
                    outObject.writeObject(Data.clients);
                    lastSentClientList = Data.clients;
                }
            }
            out.close();
            in.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("SYSTEM: Connection failed!");
        }
        //Remove client from clientlist
        Data.clients.remove(client);
    }
}
