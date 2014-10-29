package game;

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
    private GameWorld world;

    //Physics objects
    private Body body;

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
        bd.position.set((float) Math.random() * 100 + 200, (float) (Math.random() * 100 + 200));
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) world.getField().getPuckSize() / 2;
        //define fixture of the body.
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;
        //create the body and add fixture to it
        body = world.getPhysWorld().createBody(bd);
        body.createFixture(fd);
        body.setBullet(true);

        body.applyLinearImpulse(new Vec2(direction.x * speed, direction.y * speed), body.getPosition());
    }

    public Vec2 getPosition() {
        return world.getField().RotateVector2(body.getPosition(), (float) Math.PI);
    }

    public Vec2 rotateVector(Body b) {
        return world.getField().RotateVector2(b.getPosition(), (float) Math.PI);
    }

    /**
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        body.setLinearVelocity(new Vec2(direction.x * speed, direction.y * speed));
    }

    public Vec2 getVelocity() {
        return body.getLinearVelocity();
    }

    public Pod getLastTouched() {
        return this.touched.get(0);
    }

    /**
     *
     * @param touched
     */
    public void addTouched(Pod touched) {
        this.touched.add(0, touched);
    }

}
