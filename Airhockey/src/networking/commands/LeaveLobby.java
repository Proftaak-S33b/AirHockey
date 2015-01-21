package networking.commands;

import networking.IPlayer;

/**
 *
 * @author Etienne
 */
public class LeaveLobby implements GameCommand{
    
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
    
    @Override
    public void Execute() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetReceiver(GameReceiver receiver) {
	this.receiver = receiver;
    }
}
