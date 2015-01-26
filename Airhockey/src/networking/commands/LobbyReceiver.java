/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import networking.standalone.Lobby;

/**
 *
 * @author Joris
 */
public class LobbyReceiver extends Receiver {

    private Lobby lobby;

    /**
     * Instantiates a new lobbyReceiver to process incoming commands concerning
     * lobby's.
     *
     * @param lobby this receiver's Lobby object
     */
    public LobbyReceiver(Lobby lobby) {
        this.lobby = lobby;

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Command command = queue.take();
                        if (command instanceof LobbyCommand) {
                            executeCommand((LobbyCommand) command);
                        }
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    /**
     *
     * @param command
     */
    private void executeCommand(LobbyCommand command) {

    }

    /**
     * Gets this receiver's Lobby object
     *
     * @return
     */
    public Lobby getLobby() {
        return this.lobby;
    }

    /**
     *
     */
    public void readyStates() {

    }
}
