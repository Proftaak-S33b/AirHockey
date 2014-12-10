/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.Serializable;

/**
 * A class to contain data about an Airhockey game
 *
 * @author Joris
 */
public class GameData implements Serializable {

    private Coordinate redPod;
    private Coordinate bluePod;
    private Coordinate greenPod;
    private Coordinate puck;
    private Coordinate puckVelocity;
    private int scorePlayer1;
    private int scorePlayer2;
    private int scorePlayer3;
    private int round;

    /**
     * An object which contains all information about a game
     */
    public GameData() {
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
     */
    public void setRedPodPos(float x, float y) {
        redPod = new Coordinate(x, y);
    }

    /**
     * Sets a new blue pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     */
    public void setBluePodPos(float x, float y) {
        bluePod = new Coordinate(x, y);
    }

    /**
     * Sets a new green pod position
     *
     * @param x the new X position of the pod
     * @param y the new Y position of the pod
     */
    public void setGreenPodPos(float x, float y) {
        greenPod = new Coordinate(x, y);
    }

    /**
     * Sets a new puck position
     *
     * @param x the new X position of the puck
     * @param y the new Y position of the puck
     */
    public void setPuckPos(float x, float y) {
        puck = new Coordinate(x, y);
    }

    /**
     * Sets a new puck velocity
     *
     * @param x the new X component of the puck's velocity
     * @param y the new X component of the puck's velocity
     */
    public void setPuckVelocity(float x, float y) {
        puckVelocity = new Coordinate(x, y);
    }

    /**
     * Gets the position of the red pod
     *
     * @return Coordinate of the red pod position
     */
    public Coordinate getRedPodPos() {
        return redPod;
    }

    /**
     * Gets the position of the blue pod
     *
     * @return Coordinate of the blue pod position
     */
    public Coordinate getBluePodPos() {
        return bluePod;
    }

    /**
     * Gets the position of the green pod
     *
     * @return Coordinate of the green pod position
     */
    public Coordinate getGreenPodPos() {
        return greenPod;
    }

    /**
     * Gets the position of the puck
     *
     * @return Coordinate of the puck position
     */
    public Coordinate getPuckPos() {
        return puck;
    }

    /**
     * Gets the velocity of the puck as a vector (Coordinate)
     *
     * @return Coordinate with x and y velocity of the puck
     */
    public Coordinate getPuckVelocity() {
        return puckVelocity;
    }

    /**
     * Sets the score for player 1
     *
     * @param score The new score
     */
    public void setScoreP1(int score) {
        scorePlayer1 = score;
    }

    /**
     * Sets the score for player 2
     *
     * @param score The new score
     */
    public void setScoreP2(int score) {
        scorePlayer2 = score;
    }

    /**
     * Sets the score for player 3
     *
     * @param score The new score
     */
    public void setScoreP3(int score) {
        scorePlayer3 = score;
    }

    /**
     * Gets the score for player 1
     *
     * @return The score for player 1
     */
    public int getScoreP1() {
        return scorePlayer1;
    }

    /**
     * Gets the score for player 2
     *
     * @return The score for player 2
     */
    public int getScoreP2() {
        return scorePlayer2;
    }

    /**
     * Gets the score for player 3
     *
     * @return The score for player 3
     */
    public int getScoreP3() {
        return scorePlayer3;
    }

    /**
     * Sets the current round of the game
     * @param round the current round
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Gets the current round
     * @return the current round
     */
    public int getRound() {
        return this.round;
    }
}
