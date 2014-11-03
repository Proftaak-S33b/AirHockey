package z_OLD_game;

import java.util.ArrayList;
import java.util.Observable;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Puck extends Observable {

    //Game objects
    private int speed;
    private final Vec2 direction;
    private final ArrayList<Pod> touched;
    private final GameWorld world;

    //Physics objects
    private final Body body;

    /**
     *
     * @param world
     * @param speed
     */
    public Puck(GameWorld world, int speed) {
        this.speed = speed;
        direction = new Vec2(-0.5f, 1);
        direction.normalize();
        touched = new ArrayList<>();
        this.world = world;

        //body definition
        BodyDef bd = new BodyDef();
        bd.position.set((float) Math.random() + 20, (float) (Math.random() + 20));
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) world.getField().getPuckSize() / 2;
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