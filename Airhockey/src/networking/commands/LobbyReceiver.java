/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import networking.IPlayer;
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
        Receiver();
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
        command.setReceiver(this);
        command.execute();
        System.out.println("Executed LobbyCommand: " + command.getClass().getSimpleName());
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
    public void lobbyChanged(){
        sendToAll(lobby);
        System.out.println("Sent lobby \"" + lobby.getGameName() + "\" to all");
        System.out.println("Amount of players: " + lobby.getPlayersAmount());
    }

    /**
     *
     * @param me
     */
    public void readyStates(IPlayer me) {
        int index = lobby.getAllPlayers().indexOf(me);
        if(lobby.getPlayerStates().get(index)){
            lobby.setPlayerState(index, false);
        }else{
            lobby.setPlayerState(index, true);
        }
        lobbyChanged();
    }
}
