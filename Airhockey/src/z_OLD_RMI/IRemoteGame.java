/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z_OLD_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Joris
 */
public interface IRemoteGame extends Remote {

    /**
     * Only to be used by the Blue player. Sets the new position for the blue
     * player's pod.
     *
     * @param newPosition
     * @throws RemoteException
     */
    public void setBluePodPos(Coordinate newPosition) throws RemoteException;

    /**
     * Only to be used by the Green player. Sets the new position for the green
     * player's pod.
     *
     * @param newPosition
     * @throws RemoteException
     */
    public void setGreenPodPos(Coordinate newPosition) throws RemoteException;

    /**
     * Sets the data for managing the game from the host's side
     *
     * @param newPodPos The new red pod position
     * @param newPuckPos The new puck position
     * @param newPuckVel The new puck velocity
     * @param score1 the new score for player 1
     * @param score2 the new score for player 2
     * @param score3 the new score for player 3
     * @param round the current game round
     * @throws RemoteException
     */
    public void setHostData(Coordinate newPodPos, Coordinate newPuckPos, Coordinate newPuckVel,
            int score1, int score2, int score3, int round) throws RemoteException;

    /**
     * Gets the game data
     *
     * @return the game data
     * @throws RemoteException RMI
     */
    public GameData getGameData() throws RemoteException;
}
