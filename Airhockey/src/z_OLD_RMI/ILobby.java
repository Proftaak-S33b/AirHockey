/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z_OLD_RMI;

import fontys.observer.RemotePublisher;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joris
 */
public interface ILobby extends Remote, RemotePublisher{
    
    /**
     * Gets the ranking of the host
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public int getHostRank() throws RemoteException;

    /**
     * Gets the ranking of the specified player
     *
     * @param player
     * @return
     * @throws java.rmi.RemoteException
     */
    public int getRanking(IPlayer player) throws RemoteException;

    /**
     * Returns the current game name
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public String getGameName() throws RemoteException;

    /**
     * Sets a new game name
     *
     * @param gameName length has to be {@code < 20 characters}
     * @throws java.rmi.RemoteException
     */
    public void setGameName(String gameName) throws RemoteException;

    /**
     * Adds a player if
     * <ul>
     * <li>a) the name is not already used by another player in the lobby </li>
     * <li>b) the amount of players in the lobby is {@code < 3} </li>
     * </ul>
     *
     * @param player
     * @return true: succeeded in adding, false: adding failed.
     * @throws java.rmi.RemoteException
     */
    public boolean addPlayer(IPlayer player) throws RemoteException;

    /**
     * Gets the IPlayer with the specified index
     *
     * @param i i {@code < 3} or will return null
     * @return
     * @throws java.rmi.RemoteException
     */
    public IPlayer getPlayer(int i) throws RemoteException;

    public List<IPlayer> getAllPlayers() throws RemoteException;

    /**
     * Gets the IPlayer with the specified name
     *
     * @param name
     * @return null if name not found
     * @throws java.rmi.RemoteException
     */
    public IPlayer getPlayer(String name) throws RemoteException;

    /**
     * Removes the specified player from the Lobby
     *
     * @param player
     * @return false if player not found; true if removal succeeded
     * @throws java.rmi.RemoteException
     */
    public boolean removePlayer(IPlayer player) throws RemoteException;

    /**
     * Gets the amount of players in the lobby
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public int getPlayersAmount() throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public IRemoteGame getRemoteGame() throws RemoteException;
    
    /**
     * 
     * @return
     * @throws RemoteException 
     */
    public String getLastChatMessage() throws RemoteException;
    
    /**
     * 
     * @param message
     * @throws RemoteException 
     */
    public void setLastChatMessage(String message) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Boolean> getPlayerStates() throws RemoteException;
    
    /**
     * 
     * @param PlayerNr
     * @param State
     * @throws RemoteException 
     */
    public void setPlayerState(int PlayerNr, boolean State)throws RemoteException;
}
