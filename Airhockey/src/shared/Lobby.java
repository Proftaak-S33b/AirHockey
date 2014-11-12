/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import game.IPlayer;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Joris
 */
public class Lobby implements Serializable {

    private String gameName;

    private final ArrayList<IPlayer> players;

    /**
     * Instantiates a new Lobby with specified game name and host IPlayer
     *
     * @param gameName
     * @param host
     */
    public Lobby(String gameName, IPlayer host) {
        this.gameName = gameName;
        players = new ArrayList<>();
        players.add(host);
    }

    /**
     * Gets the ranking of the host
     *
     * @return
     */
    public int getHostRank() {
        return players.get(0).getRanking();
    }

    /**
     * Gets the ranking of the specified player
     *
     * @param player
     * @return
     */
    public int getRanking(IPlayer player) {
        for (IPlayer p : players) {
            if (p.equals(player)) {
                return p.getRanking();
            }
        }
        return -1;
    }

    /**
     * Returns the current game name
     *
     * @return
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets a new game name
     *
     * @param gameName length has to be {@code < 20 characters}
     */
    public void setGameName(String gameName) {

        this.gameName = gameName;
    }

    /**
     * Adds a player if
     * <ul>
     * <li>a) the name is not already used by another player in the lobby </li>
     * <li>b) the amount of players in the lobby is {@code < 3} </li>
     * </ul>
     *
     * @param player
     * @return true: succeeded in adding, false: adding failed.
     */
    public boolean addPlayer(IPlayer player) {
        boolean succeeded = false;
        if (players.size() == 3) {
            return succeeded;
        }
        for (IPlayer p : players) {
            if (!p.getName().equals(player.getName())) {
                succeeded = true;
                break;
            }
        }
        if (succeeded) {
            players.add(player);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the IPlayer with the specified index
     *
     * @param i i {@code < 3} or will return null
     * @return
     */
    public IPlayer getPlayer(int i) {
        if (i < 3) {
            return players.get(i);
        } else {
            return null;
        }
    }

    /**
     * Gets the IPlayer with the specified name
     *
     * @param name
     * @return null if name not found
     */
    public IPlayer getPlayer(String name) {
        for (IPlayer p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Removes the specified player from the Lobby
     *
     * @param player
     * @return false if player not found; true if removal succeeded
     */
    public boolean removePlayer(IPlayer player) {
        return players.remove(player);
    }

    /**
     * Gets the amount of players in the lobby
     *
     * @return
     */
    public int getPlayersAmount() {
        return players.size();
    }
}
