/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.Serializable;

/**
 *
 * @author Joris
 */
public interface IPlayer extends Serializable {

    /**
     * Get the name of a player
     *
     * @return the name of the player
     */
    public String getName();

    /**
     * Get the ranking of a player
     *
     * @return the ranking of the player
     */
    public int getRanking();

    /**
     * @author Maikel
     *
     * @param score
     */
    public void changeRanking(Boolean score);

    /**
     * Sets a new ranking
     *
     * @param score
     */
    public void setRanking(int score);
}
