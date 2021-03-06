package networking.commands;

import networking.IPlayer;

/**
 *
 * @author Etienne
 */
public class LeaveLobby implements GameCommand {
    
    int id;
    IPlayer player;
    
    /**
     * Leaves a given lobby.
     * @param receiver 
     */
    public LeaveLobby(GameReceiver receiver){
	this.id = id;
	this.player = player;
    }
    
    public GameReceiver receiver; 
    
    /**
     * Execute leaveLobby
     */
    @Override
    public void execute() {
	//receiver.leaveLobby(id, player);
    }

    /**
     * set the receiver
     * @param receiver 
     */
    @Override
    public void setReceiver(GameReceiver receiver) {
	this.receiver = receiver;
    }
}
