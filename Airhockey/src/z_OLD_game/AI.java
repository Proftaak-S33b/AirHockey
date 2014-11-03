package z_OLD_game;

import java.util.Observable;
import java.util.Observer;
import org.jbox2d.common.Vec2;

/**
 * Artificial Intelligence implementation for Player interface.
 * @author Etienne
 */
public class AI extends Observable implements Player, Observer{

    private final String name;
    //private final Difficulty diff;
    private Vec2 direction; // puck direction
    //private AI_Strategy strategy;
    
    /**
     * Creates a new AI object with a given name.
     * @param name String representing the given name of the AI.
     */
    public AI(String name) {
        this.name = name;
        //this.diff = Difficulty.EASY; Dynamize for strategy use.
    }

    @Override
    public String getName() {
        return this.name;
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
