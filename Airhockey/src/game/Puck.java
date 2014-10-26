package game;

import java.util.ArrayList;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Puck {

    //Game objects
    private Vec2 position;
    private int speed;
    private final Vec2 direction;
    private final ArrayList<Pod> touched;
    
    //Physics objects
    private Body body;

    /**
     *
     * @param world
     * @param speed
     */
    public Puck(GameWorld world, int speed) {
        this.speed = speed;
        position = new Vec2((float) Math.random() * 100 + 200, (float) (Math.random() * 100 + 200));
        direction = new Vec2((float) Math.random() * 100 + 200, (float) (Math.random() * 100 + 200));
        touched = new ArrayList<>();
        
        //body definition
        BodyDef bd = new BodyDef();
        bd.position.set((float) position.x, (float) position.y);
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) world.getField().getPuckSize();
        //define fixture of the body.
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;
        //create the body and add fixture to it
        body = world.getPhysWorld().createBody(bd);
        body.createFixture(fd);
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

    /**
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Vec2 getDirection() {
        return this.direction;
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
