/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Joris
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    private String gameName;

    private final ArrayList<IPlayer> players;
    
    private final IRemoteGame game;

    /**
     * Instantiates a new Lobby with specified game name and host IPlayer
     *
     * @param gameName
     * @param host
     * @throws java.rmi.RemoteException
     */
    public Lobby(String gameName, IPlayer host) throws RemoteException {
        this.gameName = gameName;
        players = new ArrayList<>();
        players.add(host);
        game = new RemoteGame();
    }

    /**
     * Gets the ranking of the host
     *
     * @return
     */
    @Override
    public int getHostRank() throws RemoteException {
        return players.get(0).getRanking();
    }

    /**
     * Gets the ranking of the specified player
     *
     * @param player
     * @return
     */
    @Override
    public int getRanking(IPlayer player) throws RemoteException {
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
    @Override
    public String getGameName() throws RemoteException {
        return gameName;
    }

    /**
     * Sets a new game name
     *
     * @param gameName length has to be {@code < 20 characters}
     */
    @Override
    public void setGameName(String gameName) throws RemoteException {

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
    @Override
    public boolean addPlayer(IPlayer player) throws RemoteException {
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
    @Override
    public IPlayer getPlayer(int i) throws RemoteException {
        if (i < 3) {
            return players.get(i);
        } else {
            return null;
        }
    }

    @Override
    public List<IPlayer> getAllPlayers() throws RemoteException {
        return Collections.unmodifiableList(players);
    }

    /**
     * Gets the IPlayer with the specified name
     *
     * @param name
     * @return null if name not found
     */
    @Override
    public IPlayer getPlayer(String name) throws RemoteException {
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
    @Override
    public boolean removePlayer(IPlayer player) throws RemoteException {
        return players.remove(player);
    }

    /**
     * Gets the amount of players in the lobby
     *
     * @return
     */
    @Override
    public int getPlayersAmount() throws RemoteException {
        return players.size();
    }

    /**
     * 
     * @return
     * @throws RemoteException 
     */
    @Override
    public IRemoteGame getRemoteGame() throws RemoteException {
        return game;
    }
}
