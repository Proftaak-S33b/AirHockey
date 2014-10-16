/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author Joris Douven
 */
public class Physics implements ContactListener{

    //Game objects
    private final World world;
    private final Puck puck;
    private final Pod pod1;
    private final Pod pod2;
    private final Pod pod3;
    private final Field field;

    //Physics objects
    private Body bodyPuck;
    private Body bodyPod1;
    private Body bodyPod2;
    private Body bodyPod3;
    private Body bodyRedSide;
    private Body bodyBlueSide;
    private Body bodyGreenSide;

    public Physics(Puck puck, Pod pod1, Pod pod2, Pod pod3, Field field) {
        world = new World(new Vec2(0.0f, 0.0f));
        this.puck = puck;
        this.pod1 = pod1;
        this.pod2 = pod2;
        this.pod3 = pod3;
        this.field = field;
        try {
            initBodies();
        } catch (NullPointerException ex) {
            System.out.println("null parameter not allowed in contructor of Physics");
        }
    }

    private void initBodies() {

        /**
         * **PUCK**
         */
        //body definition
        BodyDef bd = new BodyDef();
        bd.position.set((float) puck.getPosition().x, (float) puck.getPosition().y);
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        CircleShape cs = new CircleShape();
        cs.m_radius = (float) field.getPuckSize();
        //define fixture of the body.
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;
        //create the body and add fixture to it
        bodyPuck = world.createBody(bd);
        bodyPuck.createFixture(fd);

        /**
         * **POD1**
         */
        //body definition
        bd = new BodyDef();
        bd.position.set((float) pod1.getPosition().x, (float) pod1.getPosition().y);
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        cs = new CircleShape();
        cs.m_radius = (float) field.getPodSize();
        //define fixture of the body.
        fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 1f;
        //create the body and add fixture to it
        bodyPod1 = world.createBody(bd);
        bodyPod1.createFixture(fd);

        /**
         * **POD2**
         */
        //body definition
        bd = new BodyDef();
        bd.position.set((float) pod2.getPosition().x, (float) pod2.getPosition().y);
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        cs = new CircleShape();
        cs.m_radius = (float) field.getPodSize();
        //define fixture of the body.
        fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 1f;
        //create the body and add fixture to it
        bodyPod2 = world.createBody(bd);
        bodyPod2.createFixture(fd);

        /**
         * **POD3**
         */
        //body definition
        bd = new BodyDef();
        bd.position.set((float) pod3.getPosition().x, (float) pod3.getPosition().y);
        bd.type = BodyType.DYNAMIC;
        //define shape of the body.
        cs = new CircleShape();
        cs.m_radius = (float) field.getPodSize();
        //define fixture of the body.
        fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.5f;
        fd.friction = 0.3f;
        fd.restitution = 1f;
        //create the body and add fixture to it
        bodyPod3 = world.createBody(bd);
        bodyPod3.createFixture(fd);

        /**
         * **Red side**
         */
        //body definition
        BodyDef bdSide = new BodyDef();
        bdSide.position.set((float) (field.getFieldCorners().get(2).x - field.getFieldCorners().get(0).x),
                ((float) (field.getFieldCorners().get(2).y - field.getFieldCorners().get(0).y)));
        bdSide.type = BodyType.STATIC;
        //define shape of the body.
        EdgeShape esSide = new EdgeShape();
        esSide.set(new Vec2((float) field.getFieldCorners().get(2).x, (float) field.getFieldCorners().get(2).y),
                new Vec2((float) field.getFieldCorners().get(0).x, (float) field.getFieldCorners().get(0).y));
        //define fixture of the body.
        FixtureDef fdSide = new FixtureDef();
        fdSide.shape = esSide;
        fdSide.friction = 0.3f;
        fdSide.restitution = 1f;
        //create the body and add fixture to it
        bodyRedSide = world.createBody(bdSide);
        bodyRedSide.createFixture(fdSide);
        
        /**
         * **Blue side**
         */
        //body definition
        bdSide = new BodyDef();
        bdSide.position.set((float) (field.getFieldCorners().get(0).x - field.getFieldCorners().get(1).x),
                ((float) (field.getFieldCorners().get(0).y - field.getFieldCorners().get(1).y)));
        bdSide.type = BodyType.STATIC;
        //define shape of the body.
        esSide = new EdgeShape();
        esSide.set(new Vec2((float) field.getFieldCorners().get(0).x, (float) field.getFieldCorners().get(0).y),
                new Vec2((float) field.getFieldCorners().get(1).x, (float) field.getFieldCorners().get(1).y));
        //define fixture of the body.
        fdSide = new FixtureDef();
        fdSide.shape = esSide;
        fdSide.friction = 0.3f;
        fdSide.restitution = 1f;
        //create the body and add fixture to it
        bodyBlueSide = world.createBody(bdSide);
        bodyBlueSide.createFixture(fdSide);
        
        /**
         * **Green side**
         */
        //body definition
        bdSide = new BodyDef();
        bdSide.position.set((float) (field.getFieldCorners().get(1).x - field.getFieldCorners().get(2).x),
                ((float) (field.getFieldCorners().get(1).y - field.getFieldCorners().get(2).y)));
        bdSide.type = BodyType.STATIC;
        //define shape of the body.
        esSide = new EdgeShape();
        esSide.set(new Vec2((float) field.getFieldCorners().get(1).x, (float) field.getFieldCorners().get(1).y),
                new Vec2((float) field.getFieldCorners().get(2).x, (float) field.getFieldCorners().get(2).y));
        //define fixture of the body.
        fdSide = new FixtureDef();
        fdSide.shape = esSide;
        fdSide.friction = 0.3f;
        fdSide.restitution = 1f;
        //create the body and add fixture to it
        bodyGreenSide = world.createBody(bdSide);
        bodyGreenSide.createFixture(fdSide);
    }

    @Override
    public void beginContact(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void endContact(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
