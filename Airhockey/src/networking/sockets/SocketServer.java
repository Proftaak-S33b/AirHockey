package networking.sockets;

import java.io.*;
import java.net.*;

/**
 * Stub for future socket implementation.
 * @author Etienne
 */
public class SocketServer {
                
    ServerSocket serversocket;
    Socket clientsocket;
    PrintWriter out;
    BufferedReader in;
    String inputline;    
    int portnumber;
    
    /**
     * 
     */
    public void getInput() throws IOException{
        while ((inputline = in.readLine()) != null) {
                out.println(inputline);
            }
    }
    
    /**
     * Opens the streams for the clientsocket.
     * @throws IOException 
     */
    public void openStreams() throws IOException{
        
        //
        out = new PrintWriter(
                clientsocket.getOutputStream(), true);
        
        //
        in = new BufferedReader(
                new InputStreamReader(clientsocket.getInputStream()));
    }
    
    /**
     * Creates the socket of the server and one for the client.
     * @param portnumber an int with the number of the port.
     * @throws IOException 
     */
    public void createSockets(int portnumber) throws IOException{
        
        //
        serversocket = new ServerSocket(portnumber);
        
        //
        clientsocket = serversocket.accept();
    }
    
    /**
     * Writes a message to the client.
     * @param message 
     */
    public void writeMessage(String message){
        out.println(message);
    }
    
    /**
     * Creates a new Server object.
     * @param portnumber an int with the number of the port to listen to.
     */
    public SocketServer(int portnumber){
        this.portnumber = portnumber;
    }            
}
