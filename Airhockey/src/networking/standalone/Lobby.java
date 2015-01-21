/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.util.*;
import networking.IPlayer;

/**
 *
 * @author Joris
 */
public class Lobby {

    private int ID;
    private String gameName;
    private final ArrayList<IPlayer> players;
    private List<Boolean> playerStates;

    public Lobby(int ID, String gameName, IPlayer host) {
        this.ID = ID;
        playerStates = new ArrayList<>();
        this.playerStates.add(false);
        this.playerStates.add(false);
        this.playerStates.add(false);
        this.gameName = gameName;
        players = new ArrayList<>();
        players.add(host);
    }

    public int getID() {
        return ID;
    }

    public void setID(int newID) {
        this.ID = newID;
    }

    /**
     * Returns the current game name
     *
     * @return the name of the game
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
     * @return the player from position in the list
     */
    public IPlayer getPlayer(int i) {
        if (i < 3) {
            return players.get(i);
        } else {
            return null;
        }
    }

    /**
     * get all players from the player list
     * @return a unmodifiableList of players
     */
    public List<IPlayer> getAllPlayers() {
        return Collections.unmodifiableList(players);
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
        boolean succeeded = players.remove(player);
        return succeeded;
    }

    /**
     * Gets the amount of players in the lobby
     *
     * @return
     */
    public int getPlayersAmount() {
        return players.size();
    }

    /**
     * returns a list with a readu states from the players
     * @return the states of the players
     */
    public List<Boolean> getPlayerStates() {
        return this.playerStates;
    }

    /**
     * set the states of a player
     * @param playerNr the number of the player
     * @param state the state to be set
     */
    public void setPlayerState(int playerNr, boolean state) {
        if (playerNr > -1 && playerNr < 3) {
            this.playerStates.set(playerNr, state);
        }
    }
}
