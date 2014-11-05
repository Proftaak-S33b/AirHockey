/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joris
 */
public class Goal {
    private final Body body;
    private final GameWorld world;
    private final IPlayer player;
    
    public Goal(Vec2 point1, Vec2 point2, IPlayer player, GameWorld world)
    {
        this.world = world;
        this.player = player;
        
        /**
         * **Red side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set(MathUtillities.getGoalCorners().get(1).x - MathUtillities.getGoalCorners().get(0).x,
                    MathUtillities.getGoalCorners().get(0).y);
            bdSide.type = BodyType.STATIC;
            bdSide.angle = 0;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(MathUtillities.getGoalCorners().get(0).x, MathUtillities.getGoalCorners().get(0).y),
                    new Vec2(MathUtillities.getGoalCorners().get(1).x, MathUtillities.getGoalCorners().get(2).y));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyRedSide = world.getPhysWorld().createBody(bdSide);
            bodyRedSide.createFixture(fdSide);
        }
        /**
         * **Blue side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set(getFieldCornersPhysx().get(0).x,
                    getFieldCornersPhysx().get(1).y);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(25.0f, 43.3f / 2), new Vec2(0, -43.3f / 2));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyBlueSide = world.getPhysWorld().createBody(bdSide);
            bodyBlueSide.createFixture(fdSide);
        }
        /**
         * **Green side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set(getFieldCornersPhysx().get(1).x,
                    getFieldCornersPhysx().get(1).y);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(0, 43.3f / 2),
                    new Vec2(25.0f, -43.3f / 2));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyGreenSide = world.getPhysWorld().createBody(bdSide);
            bodyGreenSide.createFixture(fdSide);
        }
    }
    
    public IPlayer getPlayer()
    {
        return player;
    }
}
