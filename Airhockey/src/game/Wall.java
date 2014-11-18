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
 * @author maikel
 */
public class Wall {

    private Body bodyRedLeftSide;
    private Body bodyRedRightSide;
    private Body bodyBlueLeftSide;
    private Body bodyBlueRightSide;
    private Body bodyGreenLeftSide;
    private Body bodyGreenRightSide;

    private final GameWorld world;
    private final WallID wallid;

    public Wall(WallID wallid, Vec2 point1, Vec2 point2, GameWorld world) {
        this.wallid = wallid;
        this.world = world;

        switch (wallid) {
            case BOTTOM_LEFT: /*
             **Red Left side**
             */ {
                //body definition
                BodyDef bdSide = new BodyDef();
                bdSide.position.set((MathUtillities.getFieldCorners().get(0).x + MathUtillities.getGoalCorners().get(0).x) / 2,
                        MathUtillities.getGoalCorners().get(0).y);
                bdSide.type = BodyType.STATIC;
                bdSide.angle = 0;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners().get(0).x, MathUtillities.getFieldCorners().get(0).y),
                        new Vec2(MathUtillities.getGoalCorners().get(0).x, MathUtillities.getGoalCorners().get(0).y));
                //define fixture of the body.
                FixtureDef fdSide = new FixtureDef();
                fdSide.shape = esSide;
                fdSide.friction = 0.3f;
                fdSide.restitution = 1f;
                //create the body and add fixture to it
                bodyRedLeftSide = world.getPhysWorld().createBody(bdSide);
                bodyRedLeftSide.createFixture(fdSide);
            }
            break;
            /*
             **Red Right side**
             */
            case BOTTOM_RIGHT: {
                //body definition
                BodyDef bdSide = new BodyDef();
                bdSide.position.set((MathUtillities.getFieldCorners().get(1).x + MathUtillities.getGoalCorners().get(1).x) / 2,
                        MathUtillities.getGoalCorners().get(1).y);
                bdSide.type = BodyType.STATIC;
                bdSide.angle = 0;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners().get(1).x, MathUtillities.getFieldCorners().get(1).y),
                        new Vec2(MathUtillities.getGoalCorners().get(1).x, MathUtillities.getGoalCorners().get(1).y));
                //define fixture of the body.
                FixtureDef fdSide = new FixtureDef();
                fdSide.shape = esSide;
                fdSide.friction = 0.3f;
                fdSide.restitution = 1f;
                //create the body and add fixture to it
                bodyRedRightSide = world.getPhysWorld().createBody(bdSide);
                bodyRedRightSide.createFixture(fdSide);
            }
            break;
            case LEFT_LEFT: /**
             * **Blue Left side**
             */
            {
                //body definition
                BodyDef bdSide = new BodyDef();
                bdSide.position.set((MathUtillities.getFieldCorners().get(1).x + MathUtillities.getGoalCorners().get(3).x) / 2,
                        (MathUtillities.getFieldCorners().get(1).y + MathUtillities.getGoalCorners().get(3).y) / 2);
                bdSide.type = BodyType.STATIC;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners().get(1).x, MathUtillities.getFieldCorners().get(1).y),
                        new Vec2(MathUtillities.getGoalCorners().get(3).x, MathUtillities.getGoalCorners().get(3).y));
                //define fixture of the body.
                FixtureDef fdSide = new FixtureDef();
                fdSide.shape = esSide;
                fdSide.friction = 0.3f;
                fdSide.restitution = 1f;
                //create the body and add fixture to it
                bodyBlueLeftSide = world.getPhysWorld().createBody(bdSide);
                bodyBlueLeftSide.createFixture(fdSide);
            }
            break;
            /*
             **Blue Right side**
             */
            case LEFT_RIGHT: {
                //body definition
                BodyDef bdSide = new BodyDef();
         bdSide.position.set((MathUtillities.getFieldCorners().get(0).x + MathUtillities.getGoalCorners().get(2).x) / 2,
         (MathUtillities.getFieldCorners().get(0).y + MathUtillities.getGoalCorners().get(2).y) / 2);
         bdSide.type = BodyType.STATIC;
         //define shape of the body.
         EdgeShape esSide = new EdgeShape();
         esSide.set(new Vec2(MathUtillities.getFieldCorners().get(0).x, MathUtillities.getFieldCorners().get(0).y),
         new Vec2(MathUtillities.getGoalCorners().get(2).x, MathUtillities.getGoalCorners().get(2).y));
         //define fixture of the body.
         FixtureDef fdSide = new FixtureDef();
         fdSide.shape = esSide;
         fdSide.friction = 0.3f;
         fdSide.restitution = 1f;
         //create the body and add fixture to it
         bodyBlueRightSide = world.getPhysWorld().createBody(bdSide);
         bodyBlueRightSide.createFixture(fdSide);
            }
            break;
            /*
             **Green Left side**
             */
            case RIGHT_LEFT: {
                //body definition
                BodyDef bdSide = new BodyDef();
                bdSide.position.set((MathUtillities.getFieldCorners().get(2).x + MathUtillities.getGoalCorners().get(4).x) / 2,
                        (MathUtillities.getFieldCorners().get(2).y + MathUtillities.getGoalCorners().get(5).y) / 2);
                bdSide.type = BodyType.STATIC;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners().get(2).x, MathUtillities.getFieldCorners().get(2).y),
                        new Vec2(MathUtillities.getGoalCorners().get(4).x, MathUtillities.getGoalCorners().get(4).y));
                //define fixture of the body.
                FixtureDef fdSide = new FixtureDef();
                fdSide.shape = esSide;
                fdSide.friction = 0.3f;
                fdSide.restitution = 1f;
                //create the body and add fixture to it
                bodyGreenLeftSide = world.getPhysWorld().createBody(bdSide);
                bodyGreenLeftSide.createFixture(fdSide);
            }
            break;
            /*
             **Green Right side**
             */
            case RIGHT_RIGHT: {
                //body definition
                BodyDef bdSide = new BodyDef();
                bdSide.position.set((MathUtillities.getFieldCorners().get(1).x + MathUtillities.getGoalCorners().get(5).x) / 2,
                        (MathUtillities.getFieldCorners().get(1).y + MathUtillities.getGoalCorners().get(5).y) / 2);
                bdSide.type = BodyType.STATIC;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners().get(1).x, MathUtillities.getFieldCorners().get(1).y),
                        new Vec2(MathUtillities.getGoalCorners().get(5).x, MathUtillities.getGoalCorners().get(5).y));
                //define fixture of the body.
                FixtureDef fdSide = new FixtureDef();
                fdSide.shape = esSide;
                fdSide.friction = 0.3f;
                fdSide.restitution = 1f;
                //create the body and add fixture to it
                bodyGreenRightSide = world.getPhysWorld().createBody(bdSide);
                bodyGreenRightSide.createFixture(fdSide);
            }
            break;

        }
    }

    public WallID getWallID() {
        return this.wallid;
    }
}
