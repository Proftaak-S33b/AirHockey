/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Joris
 */
public interface ILobbyData extends Remote {

    /**
     * Adds a lobby, if the host has already been registered in a lobby, the
     * creating will fail
     *
     * @param name The string representation of the Lobby, has to be unique
     * @param host The player who started this lobby
     * @return True if adding succeeded, false if adding failed.
     * @throws java.rmi.RemoteException
     */
    public boolean add(String name, IPlayer host) throws RemoteException;

    /**
     * Removes a lobby
     *
     * @param lobby the lobby object to be removed
     * @return true if success, false if failed.
     * @throws java.rmi.RemoteException
     */
    public boolean remove(Lobby lobby) throws RemoteException;

    /**
     * Gets all lobbies known by this object
     *
     * @return An ArrayList{@code<Lobby>} containing all known Lobby objects
     * @throws java.rmi.RemoteException
     */
    public List<Lobby> getAll() throws RemoteException;
}
