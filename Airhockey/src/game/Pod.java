/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import GUI.PlayerInGame;
import networking.IPlayer;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 * The pods from the gameworld
 * @author Maikel
 */
public class Pod {
    //Game objects
    private final PlayerInGame player;
    private final Body body;
    
    private final float rc = 1.74f;
    
    
     /**
     * Create a new Pod with a gameworld, player and Startposition
     * @param world
     * @param player The player this pod belongs to.
     * @param Position The start position
     */
    public Pod(GameWorld world, PlayerInGame player, Vec2 Position) {
        this.player = player;
        //body definition
        BodyDef bd = new BodyDef();
        bd.position.set(Position.x, Position.y);
        bd.type = BodyType.STATIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) MathUtillities.getPodSize() /2;
        //define fixture of the body.
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 1f;
        //create the body and add fixture to it
        body = world.getPhysWorld().createBody(bd);
        body.createFixture(fd);
        body.setUserData(this);
    }
    
     /**
     * get the player from the pod
     * @return The player this object belongs to
     */
    public PlayerInGame getPlayer() {
        return this.player;
    }

    /**
     * get the position from the pod
     * @return The position of this object
     */
    public Vec2 getPosition() {
        return body.getPosition().clone();
    }
    
     /**
     * Move to the Right with a Pod
     * @param index 0 = red, 1 = blue, 2 = green
     */
    public void moveRight(int index) {
        switch (index) {
            case 0: //  Pod 0 [Player]
                body.setTransform(
                        new Vec2(
                                body.getPosition().x + 0.25f,
                                body.getPosition().y),
                        0);
                break;
            case 1: //  Pod 1 [Blue]
                body.setTransform(
                        new Vec2(
                                body.getPosition().x + 0.1f,
                                body.getPosition().y + (rc/10)),
                        0);
                break;
            case 2: //  Pod 2 [Green]
                body.setTransform(
                        new Vec2(
                                body.getPosition().x + 0.1f,
                                body.getPosition().y - (rc/10)),
                        0);
                break;
        }
    }

    /**
     * Move to the Left with a Pod
     * @param index 0 = red, 1 = blue, 2 = green
     */
    public void moveLeft(int index) {
        switch (index) {
            case 0:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - 0.25f,
                                body.getPosition().y),
                        0);
                break;
            case 1:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - 0.1f,
                                body.getPosition().y - (rc/10)),
                        0);
                break;
            case 2:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - 0.1f,
                                body.getPosition().y + (rc/10)),
                        0);
                break;
        }
    }
    
    /**
     * Sets the position of the pod to the given position
     * @param position Vec2 object of the position where
     * the pod should be moved to.
     */
    public void setPosition(Vec2 position)
    {
        body.setTransform(position, 0);
    }
}
