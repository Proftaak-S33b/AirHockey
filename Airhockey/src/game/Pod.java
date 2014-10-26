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
    private Vec2 position;
    
    //Physics objects
    private final Body body;

    /**
     *
     * @param world GameWorld object this Pod is child of.
     * @param player The player this pod belongs to.
     * @param startPosition The starting position of this pod.
     */
    public Pod(GameWorld world, Player player, Vec2 startPosition) {
        this.player = player;
        this.position = startPosition;
        //body definition
        BodyDef bd = new BodyDef();
        bd.position.set((float) position.x, (float) position.y);
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) world.getField().getPodSize();
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

    public Player getPlayer() {
        return this.player;
    }

    public Vec2 getPosition() {
        return this.position;
    }

    /**
     *
     * @param position
     */
    public void move(Vec2 position) {
        this.position = position;
    }

}
