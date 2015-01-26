package networking.commands;

import networking.*;

/**
 * Sends movement of a player to the server.
 * @author Etienne
 */
public class SendMovement implements ServerCommand{

    IPlayer player;
    float x;
    float y;

    public SendMovement(IPlayer player, float x, float y) {
	this.player = player;
	this.x = x;
	this.y = y;
    }
        
    ServerReceiver receiver;
    
    @Override
    public void setReceiver(ServerReceiver receiver) {
	this.receiver = receiver;
    }

    @Override
    public void execute() {
	receiver.sendMovement(player, x, y);
    }
    
}
