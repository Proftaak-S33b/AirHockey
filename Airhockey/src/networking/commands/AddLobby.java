package networking.commands;

import java.io.Serializable;
import networking.IPlayer;

/**
 * Command for adding a lobby to the server.
 * @author Etienne
 */
public class AddLobby implements ServerCommand, Serializable{
    
    int id;
    String name;
    IPlayer host;
    
    /**
     * Gives the command the needed parameters.
     * @param id 
     * @param name
     * @param host 
     */
    public AddLobby(int id, String name, IPlayer host){
	this.id = id;
	this.name = name;
	this.host = host;
    }
    
    ServerReceiver receiver;

    @Override
    public void Execute() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetReceiver(ServerReceiver receiver) {
	this.receiver = receiver;
    }
}