package networking.commands;

import networking.IPlayer;

/**
 *
 * @author Etienne
 */
public class JoinLobby implements ServerCommand {
    
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
    
    /**
     * execute joinlobby
     */
    @Override
    public void execute() {
        receiver.joinLobby(id, player);
    }        

    /**
     * set the receiver
     * @param receiver 
     */
    @Override
    public void setReceiver(ServerReceiver receiver) {
	this.receiver = receiver;
    }
}
