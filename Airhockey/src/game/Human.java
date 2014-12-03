/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import networking.IPlayer;

/**
 * The Player which is a human
 * @author Maikel
 */
public class Human implements IPlayer {

    private final String name;
    private final String password;
    private int score;

    /**
     * Create a Player with a Name, Password and score
     * @param name of the player
     * @param password of the player
     */
    public Human(String name, String password, int score) {
        this.name = name;
        this.password = password;
        this.score = score;
    }

    /**
     * Get the name of a player
     * @return the name of the player
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the ranking of a player
     * @return the ranking of the player
     */
    @Override
    public int getRanking() {
        return this.score;
    }

    /**
     * set the score of the player
     * @param scoreBool true = score +1 , false = score -1 
     */
    @Override
    public void setRanking(Boolean scoreBool) {
        if (scoreBool) {
            score++;
        } else {
            score--;
        }
    }

}
