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
    
    /**
     * Execute sendMessage
     */
    @Override
    public void Execute() {
	receiver.sendMessage(message);
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
