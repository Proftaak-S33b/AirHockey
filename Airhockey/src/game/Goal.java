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
        //Physics objects
    private final Body bodyRedGoal;
    private final Body bodyBlueGoal;
    private final Body bodyGreenGoal;
    
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
            bdSide.position.set((MathUtillities.getGoalCorners().get(1).x + MathUtillities.getGoalCorners().get(0).x) /2,
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
            bodyRedGoal = world.getPhysWorld().createBody(bdSide);
            bodyRedGoal.createFixture(fdSide);
        }
        /**
         * **Blue side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set((MathUtillities.getGoalCorners().get(2).x + MathUtillities.getGoalCorners().get(3).x) /2,
                    (MathUtillities.getGoalCorners().get(2).y + MathUtillities.getGoalCorners().get(3).y) /2);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(MathUtillities.getGoalCorners().get(2).x, MathUtillities.getGoalCorners().get(2).y),
                    new Vec2(MathUtillities.getGoalCorners().get(3).x, MathUtillities.getGoalCorners().get(3).y));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyBlueGoal = world.getPhysWorld().createBody(bdSide);
            bodyBlueGoal.createFixture(fdSide);
        }
        /**
         * **Green side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set((MathUtillities.getGoalCorners().get(4).x + MathUtillities.getGoalCorners().get(5).x) /2,
                    (MathUtillities.getGoalCorners().get(4).y + MathUtillities.getGoalCorners().get(5).y) /2);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(MathUtillities.getGoalCorners().get(4).x, MathUtillities.getGoalCorners().get(4).y),
                    new Vec2(MathUtillities.getGoalCorners().get(5).x, MathUtillities.getGoalCorners().get(5).y));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyGreenGoal = world.getPhysWorld().createBody(bdSide);
            bodyGreenGoal.createFixture(fdSide);
        }
    }
    
    public IPlayer getPlayer()
    {
        return player;
    }
}
