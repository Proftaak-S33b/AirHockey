/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 * Temporary class to story data about players in for the duration of a game.
 * For use to display data in tables.
 *
 * @author Joris
 */
public class PlayerInGame {

    private String name;
    private int score;

    public PlayerInGame(String name) {
        this.name = name;
        this.score = 20;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public void changeRanking(boolean add) {
        if (add) {
            score++;
        } else {
            score--;
        }
    }
}
