package networking.commands;

/**
 *
 * @author Etienne
 */
public class LeaveLobby implements GameCommand{
    
    public LeaveLobby(GameReceiver receiver){
	this.receiver = receiver;
    }
    
    public GameReceiver receiver; 
    
    @Override
    public void Execute() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetReceiver(GameReceiver receiver) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
