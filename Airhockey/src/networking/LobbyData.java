/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Joris
 */
public class LobbyData implements Serializable {

    private final ArrayList<Lobby> lobbies;

    public LobbyData() {
        lobbies = new ArrayList<>();
    }

    /**
     * Adds a lobby, if the host has already been registered in a lobby, the
     * creating will fail
     *
     * @param name The string representation of the Lobby
     * @param host The player who started this lobby
     * @return True if adding succeeded, false if adding failed.
     */
    public boolean addLobby(String name, IPlayer host) {
        for (Lobby l : lobbies) {
            for(int i = 0; i < 3; i++){
                if(l.getPlayer(i) == host){
                    return false;
                }
            }
        }
        lobbies.add(new Lobby(name, host));
        return true;
    }
}
