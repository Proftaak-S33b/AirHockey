/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author maikel
 */
public class Wall {

    private Body body;

    private final GameWorld world;
    private final WallID wallid;

    public Wall(WallID wallid, GameWorld world, Vec2 pos1, Vec2 pos2) {
        this.world = world;
        this.wallid = wallid;
        //Calculate lenght of the wall
        float len =(float) Math.sqrt((pos1.x-pos2.x)*(pos1.x-pos2.x)+
                                    (pos1.y-pos2.y)*(pos1.y-pos2.y));

        //Create body defenition
        BodyDef bdSide = new BodyDef();
        //Set center position of the body
        bdSide.position.set((pos1.x + pos2.x) / 2, (pos1.y + pos2.y) / 2);
        //Set body type to static
        bdSide.type = BodyType.STATIC;
        //Set the angle of the body
        bdSide.angle = 0;
        
        //Create body
        body = world.getPhysWorld().createBody(bdSide);
        
        //Define fixture for the body
        FixtureDef fdSide = new FixtureDef();
        fdSide.friction = 0.3f;
        fdSide.restitution = 1f;
        
        //Define the shape of the body
        EdgeShape esSide = new EdgeShape();
        esSide.set(new Vec2(-len/2f, 0)
                , new Vec2(len/2f, 0));
        fdSide.shape = esSide;
        
        //Add shape and fixture to the body and dispose
        body.createFixture(fdSide);
        
        //Set the angle
        body.setTransform(body.getPosition(), MathUtils.atan2(pos2.y-pos1.y, pos2.x-pos1.x));
    }
    public WallID getWallID() {
        return this.wallid;
    }
}
