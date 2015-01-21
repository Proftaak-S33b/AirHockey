package networking.commands;

/**
 * Command design pattern for sending commands over sockets.
 * @author Etienne
 */
public interface Command {
    
    /**
     * Whatever the command object wants to do is called from here.
     */
    public void Execute();
    
    /**
     * To set the receiver to connect to.
     * @param receiver the receiver class for the command to interact with.
     */
}
