package game;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Pod {

    //Game objects
    private final Player player;

    //Physics objects
    public final Body body;

    //Coordinates
    private float x = 2.5f;     // X-coordinate change for Pod.
    private float y = 4.33f;    // Y-coordinate change for Pod.
    
    /**
     *
     * @param world GameWorld object this Pod is child of.
     * @param player The player this pod belongs to.
     * @param startPosition The starting position of this pod.
     */
    public Pod(GameWorld world, Player player, Vec2 startPosition) {
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
    }

    /**
     *
     * @return The player this object belongs to
     */
    public Player getPlayer() {
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
     */
    public void moveLeft(int index) {        
            
        // 0 = red, 1 = blue, 2 = green
        switch (index) {
            case 0:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x + 5,
                                body.getPosition().y),
                        0);
                break;
            case 1:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - x,
                                body.getPosition().y + y),
                        0);
                break;
            case 2:
                body.setTransform(
                        new Vec2(
                                body.getPosition().x - x,
                                body.getPosition().y - y),
                        0);
                break;            
        }
    }

    /**
     *
     * @param index
     */
    public void moveRight(int index) {
            
        // 0 = red, 1 = blue, 2 = green
        switch (index) {
            case 0:
                body.setTransform(
                        new Vec2(
                            body.getPosition().x - 5,
                            body.getPosition().y),
                        0);
                break;
            case 1:
                body.setTransform(
                        new Vec2(
                            body.getPosition().x + x,
                            body.getPosition().y - y),
                        0);
                break;
            case 2:
                body.setTransform(
                        new Vec2(
                            body.getPosition().x + x,
                            body.getPosition().y + y),
                        0);
                break;            
        }        
    }
}
