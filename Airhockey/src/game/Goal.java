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
        
        
        
        
        
        body = world.getPhysWorld().createBody(bd);
    }
    
    public IPlayer getPlayer()
    {
        return player;
    }
}
