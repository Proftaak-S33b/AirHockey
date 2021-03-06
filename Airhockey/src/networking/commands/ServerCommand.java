package networking.commands;

/**
 * Command interface for anything handled by the serverreceiver.
 * These are seperated even though they are very alike as means of encapsulation for the command objects.
 * @author Etienne
 */
public interface ServerCommand extends Command{
    
    /**
     * To set the receiver to connect to.
     * @param receiver the receiver class for the command to interact with.
     */
    public void setReceiver(ServerReceiver receiver);
}
