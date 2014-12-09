/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * A class to contain data about an Airhockey game
 *
 * @author Joris
 */
public class GameData extends UnicastRemoteObject implements IGameData {

    private Coordinate redPod;
    private Coordinate bluePod;
    private Coordinate greenPod;
    private Coordinate puck;
    private Coordinate puckVelocity;
    private int scorePlayer1;
    private int scorePlayer2;
    private int scorePlayer3;

    /**
     *
     * @throws RemoteException
     */
    public GameData() throws RemoteException {
        redPod = new Coordinate(0, 0);
        bluePod = new Coordinate(0, 0);
        greenPod = new Coordinate(0, 0);
        puck = new Coordinate(0, 0);
        puckVelocity = new Coordinate(0, 0);
        scorePlayer1 = 20;
        scorePlayer2 = 20;
        scorePlayer3 = 20;
    }

    /**
     * Sets a new red pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     * @throws RemoteException RMI
     */
    @Override
    public void setRedPodPos(float x, float y) throws RemoteException {
        redPod = new Coordinate(x, y);
    }

    /**
     * Sets a new blue pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     * @throws RemoteException RMI
     */
    @Override
    public void setBluePodPos(float x, float y) throws RemoteException {
        bluePod = new Coordinate(x, y);
    }

    /**
     * Sets a new green pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     * @throws RemoteException RMI
     */
    @Override
    public void setGreenPodPos(float x, float y) throws RemoteException {
        greenPod = new Coordinate(x, y);
    }

    /**
     * Sets a new puck position
     *
     * @param x the new X position of the puck
     * @param y the new Y position of the puck
     * @throws RemoteException RMI
     */
    @Override
    public void setPuckPos(float x, float y) throws RemoteException {
        puck = new Coordinate(x, y);
    }

    /**
     * Sets a new puck velocity
     *
     * @param x the new X component of the puck's velocity
     * @param y the new X component of the puck's velocity
     * @throws RemoteException RMI
     */
    @Override
    public void setPuckVelocity(float x, float y) throws RemoteException {
        puckVelocity = new Coordinate(x, y);
    }

    /**
     * Gets the position of the red pod
     *
     * @return Coordinate of the red pod position
     * @throws RemoteException RMI
     */
    @Override
    public Coordinate getRedPodPos() throws RemoteException {
        return redPod;
    }

    /**
     * Gets the position of the blue pod
     *
     * @return Coordinate of the blue pod position
     * @throws RemoteException RMI
     */
    @Override
    public Coordinate getBluePodPos() throws RemoteException {
        return bluePod;
    }

    /**
     * Gets the position of the green pod
     *
     * @return Coordinate of the green pod position
     * @throws RemoteException RMI
     */
    @Override
    public Coordinate getGreenPodPos() throws RemoteException {
        return greenPod;
    }

    /**
     * Gets the position of the puck
     *
     * @return Coordinate of the puck position
     * @throws RemoteException
     */
    @Override
    public Coordinate getPuckPos() throws RemoteException {
        return puck;
    }

    /**
     * Gets the velocity of the puck as a vector (Coordinate)
     *
     * @return Coordinate with x and y velocity of the puck
     * @throws RemoteException RMI
     */
    @Override
    public Coordinate getPuckVelocity() throws RemoteException {
        return puckVelocity;
    }

    /**
     * Sets the score for player 1
     *
     * @param score The new score
     * @throws RemoteException RMI
     */
    @Override
    public void setScoreP1(int score) throws RemoteException {
        scorePlayer1 = score;
    }

    /**
     * Sets the score for player 2
     *
     * @param score The new score
     * @throws RemoteException RMI
     */
    @Override
    public void setScoreP2(int score) throws RemoteException {
        scorePlayer2 = score;
    }

    /**
     * Sets the score for player 3
     *
     * @param score The new score
     * @throws RemoteException RMI
     */
    @Override
    public void setScoreP3(int score) throws RemoteException {
        scorePlayer3 = score;
    }

    /**
     * Gets the score for player 1
     *
     * @return The score for player 1
     * @throws RemoteException RMI
     */
    @Override
    public int getScoreP1() throws RemoteException {
        return scorePlayer1;
    }

    /**
     * Gets the score for player 2
     *
     * @return The score for player 2
     * @throws RemoteException RMI
     */
    @Override
    public int getScoreP2() throws RemoteException {
        return scorePlayer2;
    }

    /**
     * Gets the score for player 3
     *
     * @return The score for player 3
     * @throws RemoteException RMI
     */
    @Override
    public int getScoreP3() throws RemoteException {
        return scorePlayer3;
    }
}
