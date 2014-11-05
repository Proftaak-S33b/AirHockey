/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

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
public class Pod {
    //Game objects
    private final IPlayer player;
    
    private final Body body;
    private World world;
    
    private final float rc = 1.74f;
    
    
     /**
     *
     * @param player The player this pod belongs to.
     */
    public Pod(World world, IPlayer player, Vec2 startPosition) {
        this.player = player;

        //body definition
        BodyDef bd = new BodyDef();
        bd.position.set(startPosition.x, startPosition.y);
        bd.type = BodyType.KINEMATIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) world.getField().getPodSize() / 2;
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
     *
     * @return The player this object belongs to
     */
    public IPlayer getPlayer() {
        return this.player;
    }

    /**
     *
     * @return The position of this object
     */
    public Vec2 getPosition() {
        return body.getPosition();
    }
    
     /**
     *
     * @param index 0 = red, 1 = blue, 2 = green
     */
    public void moveLeft(int index) {
        switch (index) {
            case 0: //  Pod 0 [Player]
                body.setTransform(
                        new Vec2(
                                body.getPosition().x + 0.5f,
                                body.getPosition().y),
                        0);
                break;
            case 1: //  Pod 1 [Blue]
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - 0.1f,
                                body.getPosition().y + (rc/10)),
                        0);
                break;
            case 2: //  Pod 2 [Green]
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - 0.1f,
                                body.getPosition().y - (rc/10)),
                        0);
                break;
        }
    }

    /**
     *
     * @param index 0 = red, 1 = blue, 2 = green
     */
    public void moveRight(int index) {
        switch (index) {
            case 0:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - 0.5f,
                                body.getPosition().y),
                        0);
                break;
            case 1:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x + 0.1f,
                                body.getPosition().y - (rc/10)),
                        0);
                break;
            case 2:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x + 0.1f,
                                body.getPosition().y + (rc/10)),
                        0);
                break;
        }
    }
}
