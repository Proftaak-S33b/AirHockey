package networking.commands;

import controllers.GameManager;
import controllers.GameManager.GameType;
import java.util.concurrent.LinkedBlockingQueue;
import networking.IPlayer;

/**
 * Receiver for messages from the server during a game.
 *
 * @author Etienne
 */
public class GameReceiver extends Receiver {

    /**
     * A reference to the manager for executing incoming commands.
     */
    private GameManager gamemanager;

    //LinkedBlockingQueue is handled within abstract superclass.

    /**
     * Sets the receiver for a given gamemanager.
     *
     * @param gamemanager the controller that needs to communicate over a
     * network.
     */
    public GameReceiver(GameManager gamemanager) {
        Receiver();
        this.gamemanager = gamemanager;
        queue = new LinkedBlockingQueue<>();
	
	 Thread messageHandler = new Thread() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Command command = queue.take();
                        if (command instanceof GameCommand) {
                            executeCommand((GameCommand) command);
                        }
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        messageHandler.setDaemon(true);
        messageHandler.start();
    }

    /**
     * Executes the command received from the server.
     *
     * @param command The Command to be executed.
     */
    public void executeCommand(GameCommand command) {
        command.setReceiver(this);
        command.execute();
        System.out.println("Executed command: " + command.getClass().getSimpleName());
    }

    /**
     * Moves the pod from other players over the network.
     *
     * @param gametype the user moving.
     * @param x the new x coordinate of the pod.
     * @param y the new y coordinate of the pod.
     */
    public void sendMovement(GameType gametype, float x, float y) {
	// Movement handled by overload.
	gamemanager.player_Move(gametype, x, y);
    }

    /**
     * Shows a sent message from the server to the user.
     * Joris: ? wtf is dis
     *
     * @param message the unwrapped String from the command.
     */
    public void addMessage(String message) {
        gamemanager.addMessage(message);
    }
}
