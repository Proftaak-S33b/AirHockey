package networking.commands;

/**
 * Command interface for anything handled by the gamereceiver.
 * These are seperated even though they are very alike as means of encapsulation for the command objects.
 * @author Etienne
 */
public interface GameCommand extends Command{
        
    /**
     * To set the receiver to connect to.
     * @param receiver the receiver class for the command to interact with.
     */
    public void SetReceiver(GameReceiver receiver);
}
