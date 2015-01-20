package networking.sockets_old;

import java.io.*;
import java.net.*;

/**
 *
 * @author Etienne
 */
class SocketRunnable implements Runnable {

    /**
     * Represents the connection with the server.
     */
    Socket connection;
    
    game_protocol protocol;
    
    ObjectOutputStream out;
    
    ObjectInputStream in;
    
    /**
     * initializes a Runnable object on a given connection..
     * @param connection 
     */
    public SocketRunnable(Socket connection) {
	this.connection = connection;
	this.protocol = new game_protocol();
    }     
    
    @Override
    public void run() {
	
	try {
	    out = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
	    in = new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));
	    
	    while (connection.isConnected()) {
		// 
		//out.write();
		// 
		
		// 
		
	    }
	    
	} catch (Exception e) {
	} finally {
	    try {
		out.close();
		in.close();		
		connection.close();
	    } catch (Exception e) {		
	    }
	}
    }    
}
