package networking.sockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that handles all the hosts connections
 *
 * @author Jur
 */
public class ConnectionRunnable implements Runnable {

    private final Socket socket;
    private String buffer;
    private final ClientData client;
    private ArrayList<ClientData> lastSentClientList = null;

    public ConnectionRunnable(Socket socket) {
        this.socket = socket;
        client = new ClientData(socket.getInetAddress().toString().substring(1));
        Data.clients.add(client);
        buffer = "";
    }

    @Override
    public void run() {
        try {
            System.out.println("Client ip: " + socket.getInetAddress());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
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
                        if(buffer.equals("4444-REDDATA")){
                            Data.gameData.redPod = (Coordinate) inObject.readObject();
                            Data.gameData.puck = (Coordinate) inObject.readObject();
                            Data.gameData.puckVelocity = (Coordinate) inObject.readObject();
                            Data.gameData.scorePlayer1 = inObject.readInt();
                            Data.gameData.scorePlayer2 = inObject.readInt();
                            Data.gameData.scorePlayer3 = inObject.readInt();
                        }
                        if(buffer.equals("4444-BLUEDATA")){
                            Data.gameData.bluePod = (Coordinate) inObject.readObject();
                        }
                        if(buffer.equals("4444-GREENDATA")){
                            Data.gameData.greenPod = (Coordinate) inObject.readObject();
                        }
                    }
                }
                //Let the thread sleep for a while
                Thread.sleep(10);
                //Send heartbeat
                out.println("");
                
                //Check if last sent client list is up to date, else sent it again
                if(lastSentClientList != Data.clients){
                    out.println("4444-CLIENTDATA");
                    outObject.writeObject(Data.clients);
                    lastSentClientList = Data.clients;
                }
                
                //Send new gamedata
                out.println("4444-GAMEDATA");
                outObject.writeObject(Data.gameData);
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
