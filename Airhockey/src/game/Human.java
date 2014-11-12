/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Joris
 */
public class Human implements IPlayer{
    
    private final String name;
    private final String password;
    private final int score;
    
    /**
     *
     * @param name
     * @param password
     */
    public Human(String name, String password, int score) {
        this.name = name;
        this.password = password;
        this.score = score;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getRanking() {
        return this.score;
    }
    
}
