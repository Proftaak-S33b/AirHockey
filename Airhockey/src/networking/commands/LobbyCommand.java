/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

/**
 *
 * @author Joris
 */
public interface LobbyCommand extends Command {
    
    /**
     * To set the receiver to connect to.
     * @param receiver the receiver class for the command to interact with.
     */
    public void SetReceiver(LobbyReceiver receiver);
}
