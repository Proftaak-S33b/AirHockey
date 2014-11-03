/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author maikel
 */
public class Physics {
    org.jbox2d.dynamics.World world = new org.jbox2d.dynamics.World(new Vec2(0,0));
    
    public Physics ()
    {
    }
    
    public void CreatePod(Vec2 startPosition)
    {
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
        world.getPhysWorld().createBody(bd); 
    }
    
    public void CreatePuck()
    {
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
        world.getPhysWorld().createBody(bd);
    }
}
