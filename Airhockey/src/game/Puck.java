/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.Observable;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joris
 */
public class Puck extends Observable{
    //Game objects
    private int speed;
    private final Vec2 direction;
    private final ArrayList<Pod> touched;
    private final World world;
    
    //Physics objects
    private final Body body;
    
     /**
     *
     * @param world
     * @param speed
     */
    public Puck(World world, int speed)
    {
        this.speed = speed;
        direction = new Vec2(-0.5f, 1);
        direction.normalize();
        touched = new ArrayList<>();
        this.world = world;
        
        body = world.getPhysWorld();
    }
    
    public Vec2 getPosition() {
        return body.getPosition();
    }

    /**
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Vec2 getVelocity() {
        return body.getLinearVelocity();
    }
    
    /**
     * Gets the pod that last touched the Puck
     * @param howLongAgo 0 will get the last touched, 1 will get the 2nd last touched, etc.
     * @return 
     */
    public Pod getTouched(int howLongAgo) {
        return this.touched.get(howLongAgo);
    }

    /**
     *
     * @param touched
     */
    public void addTouched(Pod touched) {
        this.touched.add(0, touched);
    }
    
}
