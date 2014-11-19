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

    public Wall(WallID wallid, GameWorld world) {
        this.wallid = wallid;
        this.world = world;

        switch (wallid) {
            case BOTTOM_LEFT: /*
             **Red Left side**
             */ {
                //body definition
                BodyDef bdSide = new BodyDef();
                bdSide.position.set((MathUtillities.getFieldCorners(MathUtillities.Corner.A).x + MathUtillities.getGoalCorners(MathUtillities.Corner.B).x) / 2,
                        MathUtillities.getGoalCorners(MathUtillities.Corner.B).y);
                bdSide.type = BodyType.STATIC;
                bdSide.angle = 0;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners(MathUtillities.Corner.A).x, MathUtillities.getFieldCorners(MathUtillities.Corner.A).y),
                        new Vec2(MathUtillities.getGoalCorners(MathUtillities.Corner.B).x, MathUtillities.getGoalCorners(MathUtillities.Corner.B).y));
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
                bdSide.position.set((MathUtillities.getFieldCorners(MathUtillities.Corner.D).x + MathUtillities.getGoalCorners(MathUtillities.Corner.C).x) / 2,
                        MathUtillities.getGoalCorners(MathUtillities.Corner.C).y);
                bdSide.type = BodyType.STATIC;
                bdSide.angle = 0;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners(MathUtillities.Corner.D).x, MathUtillities.getFieldCorners(MathUtillities.Corner.D).y),
                        new Vec2(MathUtillities.getGoalCorners(MathUtillities.Corner.C).x, MathUtillities.getGoalCorners(MathUtillities.Corner.C).y));
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
                bdSide.position.set((MathUtillities.getFieldCorners(MathUtillities.Corner.I).x + MathUtillities.getGoalCorners(MathUtillities.Corner.F).x) / 2,
                        (MathUtillities.getFieldCorners(MathUtillities.Corner.I).y + MathUtillities.getGoalCorners(MathUtillities.Corner.F).y) / 2);
                bdSide.type = BodyType.STATIC;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners(MathUtillities.Corner.I).x, MathUtillities.getFieldCorners(MathUtillities.Corner.I).y),
                        new Vec2(MathUtillities.getGoalCorners(MathUtillities.Corner.F).x, MathUtillities.getGoalCorners(MathUtillities.Corner.F).y));
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
         bdSide.position.set((MathUtillities.getFieldCorners(MathUtillities.Corner.A).x + MathUtillities.getGoalCorners(MathUtillities.Corner.E).x) / 2,
         (MathUtillities.getFieldCorners(MathUtillities.Corner.A).y + MathUtillities.getGoalCorners(MathUtillities.Corner.E).y) / 2);
         bdSide.type = BodyType.STATIC;
         //define shape of the body.
         EdgeShape esSide = new EdgeShape();
         esSide.set(new Vec2(MathUtillities.getFieldCorners(MathUtillities.Corner.A).x, MathUtillities.getFieldCorners(MathUtillities.Corner.A).y),
         new Vec2(MathUtillities.getGoalCorners(MathUtillities.Corner.E).x, MathUtillities.getGoalCorners(MathUtillities.Corner.E).y));
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
                bdSide.position.set((MathUtillities.getFieldCorners(MathUtillities.Corner.D).x + MathUtillities.getGoalCorners(MathUtillities.Corner.G).x) / 2,
                        (MathUtillities.getFieldCorners(MathUtillities.Corner.D).y + MathUtillities.getGoalCorners(MathUtillities.Corner.G).y) / 2);
                bdSide.type = BodyType.STATIC;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners(MathUtillities.Corner.D).x, MathUtillities.getFieldCorners(MathUtillities.Corner.D).y),
                        new Vec2(MathUtillities.getGoalCorners(MathUtillities.Corner.G).x, MathUtillities.getGoalCorners(MathUtillities.Corner.G).y));
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
                bdSide.position.set((MathUtillities.getFieldCorners(MathUtillities.Corner.I).x + MathUtillities.getGoalCorners(MathUtillities.Corner.H).x) / 2,
                        (MathUtillities.getFieldCorners(MathUtillities.Corner.I).y + MathUtillities.getGoalCorners(MathUtillities.Corner.H).y) / 2);
                bdSide.type = BodyType.STATIC;
                //define shape of the body.
                EdgeShape esSide = new EdgeShape();
                esSide.set(new Vec2(MathUtillities.getFieldCorners(MathUtillities.Corner.I).x, MathUtillities.getFieldCorners(MathUtillities.Corner.I).y),
                        new Vec2(MathUtillities.getGoalCorners(MathUtillities.Corner.H).x, MathUtillities.getGoalCorners(MathUtillities.Corner.H).y));
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
