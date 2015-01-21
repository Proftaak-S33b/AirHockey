package networking.commands;

import java.io.Serializable;

/**
 * Command for sending a message to the lobbychat.
 * @author Etienne
 */
public class SendMessage implements ServerCommand, Serializable{
    
    String message;
    
    public SendMessage(String message){
	this.message = message;
    }

    public ServerReceiver receiver; 
    
    @Override
    public void Execute() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }        

    @Override
    public void SetReceiver(ServerReceiver receiver) {
	this.receiver = receiver;
    }
}
