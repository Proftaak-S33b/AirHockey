/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import networking.IPlayer;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Maikel
 */
public class Goal {
        //Physics objects
    private final Body bodyRedGoal;
    private final Body bodyBlueGoal;
    private final Body bodyGreenGoal;
    
    private final GameWorld world;
    private final IPlayer player;
    
    public Goal(IPlayer player, GameWorld world)
    {
        this.world = world;
        this.player = player;
        
        /**
         * **Red side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set((MathUtillities.getCoordinates(MathUtillities.Corner.C).x + MathUtillities.getCoordinates(MathUtillities.Corner.B).x) /2,
                    MathUtillities.getCoordinates(MathUtillities.Corner.B).y);
            bdSide.type = BodyType.STATIC;
            bdSide.angle = 0;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.B).x, MathUtillities.getCoordinates(MathUtillities.Corner.B).y),
                    new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.C).x, MathUtillities.getCoordinates(MathUtillities.Corner.C).y));
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
            bdSide.position.set((MathUtillities.getCoordinates(MathUtillities.Corner.E).x + MathUtillities.getCoordinates(MathUtillities.Corner.F).x) /2,
                    (MathUtillities.getCoordinates(MathUtillities.Corner.E).y + MathUtillities.getCoordinates(MathUtillities.Corner.F).y) /2);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.E).x, MathUtillities.getCoordinates(MathUtillities.Corner.E).y),
                    new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.F).x, MathUtillities.getCoordinates(MathUtillities.Corner.F).y));
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
            bdSide.position.set((MathUtillities.getCoordinates(MathUtillities.Corner.G).x + MathUtillities.getCoordinates(MathUtillities.Corner.H).x) /2,
                    (MathUtillities.getCoordinates(MathUtillities.Corner.G).y + MathUtillities.getCoordinates(MathUtillities.Corner.H).y) /2);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.G).x, MathUtillities.getCoordinates(MathUtillities.Corner.G).y),
                    new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.H).x, MathUtillities.getCoordinates(MathUtillities.Corner.H).y));
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
