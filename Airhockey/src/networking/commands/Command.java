package networking.commands;

import java.io.Serializable;

/**
 * Command design pattern for sending commands over sockets.
 * @author Etienne
 */
public interface Command extends Serializable {
    
    /**
     * Whatever the command object wants to do is called from here.
     */
    public void execute();
    
    /**
     * To set the receiver to connect to.
     * @param receiver the receiver class for the command to interact with.
     */
}
