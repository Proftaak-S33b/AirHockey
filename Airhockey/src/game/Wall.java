/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author maikel
 */
public class Wall {
    private final Body body;
    private final GameWorld world;
    private final WallID wallid;
    
    public Wall(WallID wallid, Vec2 point1, Vec2 point2, GameWorld world )
    {
        this.wallid = wallid;
        this.world = world;
        
        body = world.getPhysWorld().createBody(bd);
    }
    
    public WallID getWallID()
    {
        return this.wallid;
    }
}
