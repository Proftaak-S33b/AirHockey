/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joris
 */
public class LobbyData implements ILobbyData {

    private final ArrayList<Lobby> lobbies;

    public LobbyData() {
        lobbies = new ArrayList<>();
    }

    /**
     * Adds a lobby, if the host has already been registered in a lobby, the
     * creating will fail
     *
     * @param name The string representation of the Lobby, has to be unique
     * @param host The player who started this lobby
     * @return True if adding succeeded, false if adding failed.
     */
    @Override
    public boolean add(String name, IPlayer host) {
        for (Lobby l : lobbies) {
            if (l.getGameName().equals(name)) {
                return false;
            }
            for (IPlayer p : l.getAllPlayers()) {
                if (p.getName().equals(host.getName()) || p.equals(host)) {
                    return false;
                }
            }
        }
        lobbies.add(new Lobby(name, host));
        return true;
    }

    /**
     * Removes a lobby
     *
     * @param lobby the lobby object to be removed
     * @return true if success, false if failed.
     */
    @Override
    public boolean remove(Lobby lobby) {
        return lobbies.remove(lobby);
    }

    /**
     * Gets all lobbies known by this object
     *
     * @return An ArrayList{@code<Lobby>} containing all known Lobby objects
     */
    @Override
    public List<Lobby> getAll() {
        return lobbies;
    }
}
