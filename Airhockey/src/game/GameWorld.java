/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import networking.IPlayer;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 *
 * @author Maikel
 */
public class GameWorld {

    //Game objects
    private Puck puck;
    private final ArrayList<Pod> pods;
    private final ArrayList<IPlayer> players;

    //Physics object
    private final World world;

    /**
     * Creates a new GameWorld object which keeps track of all the objects in
     * the game. Puck, 3 Pods, 3 Players,
     *
     * @param players An ArrayList of Player objects that will take part in this
     * game. Only the first 3 will be used.
     */
    public GameWorld(ObservableList<IPlayer> players) {
        //Create world
        world = new World(new Vec2(0.0f, 0.0f));
        //Create puck
        puck = new Puck(10, this);
        //Create field
        new Wall(WallID.BOTTOM_LEFT, this, MathUtillities.getCoordinates(MathUtillities.Corner.A), MathUtillities.getCoordinates(MathUtillities.Corner.B));
        new Wall(WallID.BOTTOM_RIGHT, this, MathUtillities.getCoordinates(MathUtillities.Corner.C), MathUtillities.getCoordinates(MathUtillities.Corner.D));
        new Wall(WallID.LEFT_LEFT, this, MathUtillities.getCoordinates(MathUtillities.Corner.D), MathUtillities.getCoordinates(MathUtillities.Corner.G));
        new Wall(WallID.LEFT_RIGHT, this, MathUtillities.getCoordinates(MathUtillities.Corner.H), MathUtillities.getCoordinates(MathUtillities.Corner.I));
        new Wall(WallID.RIGHT_LEFT, this, MathUtillities.getCoordinates(MathUtillities.Corner.I), MathUtillities.getCoordinates(MathUtillities.Corner.E));
        new Wall(WallID.RIGHT_RIGHT, this, MathUtillities.getCoordinates(MathUtillities.Corner.F), MathUtillities.getCoordinates(MathUtillities.Corner.A));
        //Red goal
        new Goal(players.get(0),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.B).x, MathUtillities.getCoordinates(MathUtillities.Corner.B).y - 2),
                MathUtillities.getCoordinates(MathUtillities.Corner.B));
        new Goal(players.get(0),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.B).x, MathUtillities.getCoordinates(MathUtillities.Corner.B).y-2),
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.C).x, MathUtillities.getCoordinates(MathUtillities.Corner.C).y-2));
        new Goal(players.get(0),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.C).x, MathUtillities.getCoordinates(MathUtillities.Corner.C).y - 2),
                MathUtillities.getCoordinates(MathUtillities.Corner.C));
        //Green goal
        new Goal(players.get(1),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.H).x + 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.H).y + 1.2f),
                MathUtillities.getCoordinates(MathUtillities.Corner.H));
        new Goal(players.get(1),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.G).x + 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.G).y + 1.2f),
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.H).x + 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.H).y + 1.2f));
        new Goal(players.get(1),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.G).x + 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.G).y +1.2f),
                MathUtillities.getCoordinates(MathUtillities.Corner.G));
        //Blue goal
        new Goal(players.get(2),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.E).x - 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.E).y +1.2f),
                MathUtillities.getCoordinates(MathUtillities.Corner.E));
        new Goal(players.get(2),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.E).x - 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.E).y+1.2f),
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.F).x - 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.F).y+1.2f));
        new Goal(players.get(2),
                this,
                new Vec2(MathUtillities.getCoordinates(MathUtillities.Corner.F).x - 1.8f, MathUtillities.getCoordinates(MathUtillities.Corner.F).y + 1.2f),
                MathUtillities.getCoordinates(MathUtillities.Corner.F));

        //Make sure only the first 3 players in the array get added
        this.players = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.players.add(players.get(i));
        }
        //Create pods
        final ArrayList<Vec2> startPositions = MathUtillities.getStartPositions();
        pods = new ArrayList<>();
        pods.add(new Pod(this, this.players.get(0), startPositions.get(0)));
        pods.add(new Pod(this, this.players.get(1), startPositions.get(1)));
        pods.add(new Pod(this, this.players.get(2), startPositions.get(2)));
    }

    public void score(IPlayer scoredAgainst) {

    }

    /**
     * Get the player with the specified name. If no player with the specified
     * name is found, null is returned.
     *
     * @param name The name of the player
     * @return Returns the player object with the specified name.
     */
    public IPlayer getPlayer(String name) {
        for (IPlayer p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get the physics World object.
     *
     * @return jBox2D World object
     */
    public World getPhysWorld() {
        return this.world;
    }

    /**
     * Get the puck object
     *
     * @return the Puck object
     */
    public Puck getPuck() {
        return this.puck;
    }

    /**
     * Get the pod which belongs to the player with the specified name. If no
     * player with the specified name is taking part in this game, null is
     * returned.
     *
     * @param index
     * @return Returns the pod object that belongs to the player with the
     * specified name.
     */
    public Pod getPod(int index) {
        return pods.get(index);
    }

    public int findPlayerIndex(String name) throws NoSuchFieldException {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(name)) {
                return i;
            }
        }
        throw new NoSuchFieldException("No such player");
    }

    public int getScore(String playerName) {
        return -1;
    }
    
    /**
     * Resets the puck to the starting position with random direction
     */
    public void resetPuck(){
        getPhysWorld().destroyBody(puck.getBody());
        puck = new Puck(10, this);
    }

}
