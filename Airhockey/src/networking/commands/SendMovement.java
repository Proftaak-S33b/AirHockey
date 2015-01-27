package networking.commands;

import controllers.GameManager.GameType;
import networking.*;

/**
 * Sends movement of a player to the server.
 * @author Etienne
 */
public class SendMovement implements GameCommand{

    GameType gametype;
    float x;
    float y;

    public SendMovement(GameType gametype, float x, float y) {
	this.gametype = gametype;
	this.x = x;
	this.y = y;
    }
        
    GameReceiver receiver;
    
    @Override
    public void setReceiver(GameReceiver receiver) {
	this.receiver = receiver;
    }

    @Override
    public void execute() {
	receiver.sendMovement(gametype, x, y);
    }
    
}
