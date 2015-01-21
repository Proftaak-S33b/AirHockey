package networking.commands;

import java.io.Serializable;
import networking.IPlayer;
import networking.standalone.Server;

/**
 *
 * @author Etienne
 */
public class JoinLobby implements ServerCommand, Serializable{
    
    int id;
    IPlayer player;

    /**
     * Joins the lobby of the given player.
     * @param id
     * @param player 
     */
    public JoinLobby(int id, IPlayer player) {
	this.id = id;
	this.player = player;
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
