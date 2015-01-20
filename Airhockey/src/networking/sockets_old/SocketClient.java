package networking.sockets_old;

import java.io.*;
import java.net.*;

/**
 * Clientside socket implementation for server-pushing model.
 * @author Etienne
 */
public class SocketClient {
    
    InetAddress ipaddress ;//= InetAddress.getLocalHost();
    InetAddress serverip;
    //String hostname;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    BufferedReader serverinput;
    //String userinput;
    int portnumber;
    
    /**
     * 
     * @param host
     * @param port
     */
    public SocketClient(InetAddress host, int port){
        ipaddress = host;
        portnumber = port;
    }
//    
//    /**
//     * 
//     * @throws IOException 
//     */
//    public void openStreams() throws IOException{
//        // A stream for the socket to write its output to.
//        out = new PrintWriter(socket.getOutputStream(), true);
//
//        // A stream for the socket to write its input to,
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//        // Reads input from server.
//        serverinput = new BufferedReader(new InputStreamReader(System.in));
//    }
//    
//    /**
//     * 
//     * @throws IOException 
//     */
//    public void getInput() throws IOException{
//        // One line at a time gets read and written to userinput, then printed.
//        while ((userinput = serverinput.readLine()) != null) {
//            out.println(userinput);
//            System.out.println("echo: " + in.readLine());
//        }
//    }
//    
//    /**
//     * 
//     * @throws IOException 
//     */
//    public void startSocket() throws IOException{
//        socket = new Socket("localhost", portnumber);
//    }
//    
//    /**
//     * 
//     * @param message 
//     */
//    public void writeMessage(String message){
//        
//    }    
}
