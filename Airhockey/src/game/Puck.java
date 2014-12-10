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
 * The Puck from the gameworld
 *
 * @author Maikel
 */
public class Puck extends Observable {

    //Game objects
    private int speed;
    private Vec2 direction;
    private final ArrayList<Pod> touched;
    private final GameWorld world;

    //Physics objects
    private final Body body;

    /**
     * Create a new Puck with a Speed en the Gameworld The direction of the Puck
     * is random
     *
     * @param world
     * @param speed
     */
    public Puck(int speed, GameWorld world) {
        this.speed = speed;
        direction = new Vec2((float) Math.random(), (float) Math.random());
        direction.normalize();
        touched = new ArrayList<>();
        this.world = world;

        //body definition
        BodyDef bd = new BodyDef();
        bd.position.set((float) Math.random() * 10 + 20, (float) (Math.random() * 10 + 20));
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) MathUtillities.getPuckSize() / 2;
        //define fixture of the body.
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.0f;
        fd.restitution = 1.0f;
        //create the body and add fixture to it
        body = world.getPhysWorld().createBody(bd);
        body.createFixture(fd);
        body.setBullet(true);

        body.setLinearVelocity(new Vec2(direction.x * this.speed, direction.y * this.speed));
        body.setUserData(this);
    }

    /**
     * Get's the position of the puck
     *
     * @return A Vec2 object
     */
    public Vec2 getPosition() {
        return body.getPosition().clone();
    }

    /**
     * Set a new position of the puck
     *
     * @param position the new position
     */
    public void setPosition(Vec2 position) {
        body.setTransform(position, 0);
    }

    /**
     * Set a new velocity of the puck
     *
     * @param velocity the new velocity
     */
    public void setVelocity(Vec2 velocity) {
        body.setLinearVelocity(velocity);
    }

    /**
     * Gets the pod that last touched the Puck
     *
     * @param howLongAgo 0 will get the last touched, 1 will get the 2nd last
     * touched, etc.
     * @return
     */
    public Pod getTouched(int howLongAgo) {
        if (touched.isEmpty()) {
            return null;
        }
        if (howLongAgo >= touched.size()) {
            return null;
        }
        return this.touched.get(howLongAgo);
    }

    /**
     * Add a touch with a Pod
     *
     * @param touched
     */
    public void addTouched(Pod touched) {
        if (this.touched.isEmpty()) {
            System.out.println("leeg");
        } else {
            this.touched.add(1, getTouched(0));
            System.out.println(this.getTouched(1).getPlayer().getName());
        }
        this.touched.add(0, touched);
        System.out.println(this.getTouched(0).getPlayer().getName());
        System.out.println("---------------------------");
    }

    /**
     * Get the physics body of the puck
     *
     * @return The physics body object of the puck
     */
    public Body getBody() {
        return body;
    }
}
