/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import networking.IPlayer;
import java.util.Observable;
import java.util.Observer;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Etienne
 */
public class AI extends Observable implements IPlayer, Observer{

    private final String name;
    private final int score;
    private Vec2 direction; 
    
    /**
     * Creates a new AI object with a given name.
     * @param name String representing the given name of the AI.
     */
    public AI(String name, int score) {
        this.name = name;
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
    
    public Vec2 getDirection(){
        return this.direction;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Updated AI.");        
        this.direction = (Vec2)arg;
        setChanged();
        notifyObservers(direction);
        clearChanged();
    }
    
}
