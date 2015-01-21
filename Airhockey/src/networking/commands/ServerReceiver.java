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

    public void executeCommand(Command command) {
        //pass reference to command object
        command.Execute();
    }

    public void addLobby(String name, IPlayer host) {

    }

    public void removeLobby(int id) {

    }

    public void changeLobbyName(int id, String newName) {
        for (Lobby l : lobbyList) {
            if (l.getID() == id) {
                l.setGameName(newName);
            }
        }
    }

    public void joinLobby(int id, IPlayer player) {

    }
}
