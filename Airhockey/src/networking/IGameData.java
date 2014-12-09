/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Joris
 */
public interface IGameData extends Remote {

    /**
     * Sets a new red pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     * @throws RemoteException RMI
     */
    public void setRedPodPos(float x, float y) throws RemoteException;

    /**
     * Sets a new blue pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     * @throws RemoteException RMI
     */
    public void setBluePodPos(float x, float y) throws RemoteException;

    /**
     * Sets a new green pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     * @throws RemoteException RMI
     */
    public void setGreenPodPos(float x, float y) throws RemoteException;

    /**
     * Sets a new puck position
     *
     * @param x the new X position of the puck
     * @param y the new Y position of the puck
     * @throws RemoteException RMI
     */
    public void setPuckPos(float x, float y) throws RemoteException;

    /**
     * Sets a new puck velocity
     *
     * @param x the new X component of the puck's velocity
     * @param y the new X component of the puck's velocity
     * @throws RemoteException RMI
     */
    public void setPuckVelocity(float x, float y) throws RemoteException;

    /**
     * Gets the position of the red pod
     *
     * @return Coordinate of the red pod position
     * @throws RemoteException RMI
     */
    public Coordinate getRedPodPos() throws RemoteException;

    /**
     * Gets the position of the blue pod
     *
     * @return Coordinate of the blue pod position
     * @throws RemoteException RMI
     */
    public Coordinate getBluePodPos() throws RemoteException;

    /**
     * Gets the position of the green pod
     *
     * @return Coordinate of the green pod position
     * @throws RemoteException RMI
     */
    public Coordinate getGreenPodPos() throws RemoteException;

    /**
     * Gets the position of the puck
     *
     * @return Coordinate of the puck position
     * @throws RemoteException
     */
    public Coordinate getPuckPos() throws RemoteException;

    /**
     * Gets the velocity of the puck as a vector (Coordinate)
     *
     * @return Coordinate with x and y velocity of the puck
     * @throws RemoteException RMI
     */
    public Coordinate getPuckVelocity() throws RemoteException;

    /**
     * Sets the score for player 1
     *
     * @param score The new score
     * @throws RemoteException RMI
     */
    public void setScoreP1(int score) throws RemoteException;

    /**
     * Sets the score for player 2
     *
     * @param score The new score
     * @throws RemoteException RMI
     */
    public void setScoreP2(int score) throws RemoteException;

    /**
     * Sets the score for player 3
     *
     * @param score The new score
     * @throws RemoteException RMI
     */
    public void setScoreP3(int score) throws RemoteException;

    /**
     * Gets the score for player 1
     *
     * @return The score for player 1
     * @throws RemoteException RMI
     */
    public int getScoreP1() throws RemoteException;

    /**
     * Gets the score for player 2
     *
     * @return The score for player 2
     * @throws RemoteException RMI
     */
    public int getScoreP2() throws RemoteException;

    /**
     * Gets the score for player 3
     *
     * @return The score for player 3
     * @throws RemoteException RMI
     */
    public int getScoreP3() throws RemoteException;
}
