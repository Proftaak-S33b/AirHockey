package networking.commands;

/**
 * Command for sending a message to the lobbychat.
 * @author Etienne
 */
public class SendMessage implements ServerCommand {
    
    String message;
    
    public SendMessage(String message){
	this.message = message;
    }

    public ServerReceiver receiver; 
    
    /**
     * Execute sendMessage
     */
    @Override
    public void execute() {
	receiver.sendMessage(message);
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
