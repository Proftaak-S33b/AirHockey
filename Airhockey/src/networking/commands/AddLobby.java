package networking.commands;

import java.io.Serializable;
import networking.IPlayer;

/**
 * Command for adding a lobby to the server.
 * @author Etienne
 */
public class AddLobby implements ServerCommand {
    
    String name;
    IPlayer host;
    
    /**
     * Gives the command the needed parameters.
     * @param id 
     * @param name
     * @param host 
     */
    public AddLobby(String name, IPlayer host){
	this.name = name;
	this.host = host;
    }
    
    ServerReceiver receiver;

    /**
     * Execute addLobby
     */
    @Override
    public void Execute() {
	receiver.addLobby(name, host);
    }

    /**
     * set the receiver
     * @param receiver 
     */
    @Override
    public void SetReceiver(ServerReceiver receiver) {
	this.receiver = receiver;
    }
}