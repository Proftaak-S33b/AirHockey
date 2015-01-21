/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import java.util.ArrayList;
import java.util.List;
import networking.IPlayer;
import networking.standalone.Lobby;

/**
 *
 * @author Etienne
 */
public class ServerReceiver {

    private static int NEXTLOBBYID = 10000;

    private List<Lobby> lobbyList;

    public ServerReceiver() {
        lobbyList = new ArrayList<>();
    }

    /**
     * Exectuce the command
     *
     * @param command The command to be executed
     */
    public void executeCommand(Command command) {
        //pass reference to command object
        command.Execute();
    }

    /**
     * Add a lobby
     *
     * @param name the name of the lobby
     * @param host the host of the lobby
     */
    public void addLobby(String name, IPlayer host) {

    }

    /**
     * remove a lobby
     *
     * @param id the id of the lobby
     */
    public void removeLobby(int id) {
        
    }

    /**
     * change the name of the lobby
     * @param id the id of the lobby
     * @param newName the new name of the lobby
     */
    public void changeLobbyName(int id, String newName) {
        for (Lobby l : lobbyList) {
            if (l.getID() == id) {
                l.setGameName(newName);
            }
        }
    }

    /**
     * join a lobby
     * add a player to the lobby
     * @param id the id of the lobby
     * @param player the player which will join the lobby
     */
    public void joinLobby(int id, IPlayer player) {

    }
}
