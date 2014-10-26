package game;

import java.util.ArrayList;
import java.util.Observable;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Puck extends Observable{

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
        setSpeed(speed);
    }

    public Vec2 getPosition() {
        return world.getField().RotateVector2(body.getPosition(), (float) Math.PI);
    }

    /**
     *
     * @param position
     */
    public void move(Vec2 position) {
        body.setTransform(position, 0);
        setChanged(); System.out.println("1/2: puck set to changed.");
        notifyObservers(position);
        clearChanged(); System.out.println("2/2: puck change cleared.");
    }

    /**
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        direction.normalize();
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
