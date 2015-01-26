package networking.commands;

import controllers.GameManager;
import java.util.concurrent.LinkedBlockingQueue;
import networking.IPlayer;

/**
 * Receiver for messages from the server during a game.
 * @author Etienne
 */
<<<<<<< HEAD
public class GameReceiver {
    
    /**
     * A reference to the manager for executing incoming commands.
     */
    GameManager gamemanager;
    
    /**
     * Ask Joris.
     */
    LinkedBlockingQueue queue;

    
    /**
     * Sets the receiver for a given gamemanager.
     * @param gamemanager the controller that needs to communicate over a network.
     */
    public GameReceiver(GameManager gamemanager){
	this.gamemanager = gamemanager;
        queue = new LinkedBlockingQueue<>();
    }
    
    /**
     * Executes the command received from the server.
     * @param command The Command to be executed.
     */
    public void executeCommand(GameCommand command) {
        command.setReceiver(this);
        command.execute();
        System.out.println("Executed command: " + command.getClass().getSimpleName());
    }
    
    /**
     * Moves the pod from other players over the network.
     * @param player the user moving.
     * @param x the new x coordinate of the pod.
     * @param y the new y coordinate of the pod.
     */
    public void applyMovement(IPlayer player, float x, float y){
	// decide who to move
	//gamemanager.gameworld.getPlayer(player.getName());
	// decide where to move
	
	// move
	
=======
public class GameReceiver extends Receiver {

    /**
     *
     */
    public GameReceiver() {
        Receiver();
    }

    /**
     *
     */
    public void moveLeft() {

    }

    /**
     *
     */
    public void moveRight() {

>>>>>>> a56b4174cc40f0116d5923849596f869b6023123
    }
    
    /**
     * Shows a sent message from the server to the user.
     * @param message the unwrapped String from the command.
     */
    public void addMessage(String message){
	gamemanager.addMessage(message);
    }
    
    
}
