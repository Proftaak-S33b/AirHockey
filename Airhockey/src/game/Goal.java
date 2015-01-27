/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import GUI.PlayerInGame;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 * The goals from the gameworld
 * @author Maikel
 */
public class Goal {
    private final GameWorld world;
    private final PlayerInGame player;

    //Physics object
    private Body body;

    
    /**
     * Create a new Goal with a Player, World, Left-position, Right-Position
     * @param player the goal from a player
     * @param world
     * @param pos1 left position of the goal
     * @param pos2 right position of the goal
     */
    public Goal(PlayerInGame player, GameWorld world, Vec2 pos1, Vec2 pos2) {
        this.world = world;
        this.player = player;
        //Calculate lenght of the goal
        float len = (float) Math.sqrt((pos1.x - pos2.x) * (pos1.x - pos2.x)
                + (pos1.y - pos2.y) * (pos1.y - pos2.y));

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
        esSide.set(new Vec2(-len / 2f, 0), new Vec2(len / 2f, 0));
        fdSide.shape = esSide;

        //Add shape and fixture to the body and dispose
        body.createFixture(fdSide);

        //Set the angle
        body.setTransform(body.getPosition(), MathUtils.atan2(pos2.y - pos1.y, pos2.x - pos1.x));

        //Set the user data so that we can track physics items
        body.setUserData(this);
    }

    /**
     * 
     * get the player from the goal
     * @return the player
     */
    public PlayerInGame getPlayer() {
        return player;
    }
}
