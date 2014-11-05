/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 *
 * @author Joris
 */
public class GameWorld {    
    //Game objects
    private Puck puck;
    private final ArrayList<Pod> pods;
    private final transient ObservableList<Pod> observablePods;
    private final ArrayList<IPlayer> players;
    private final transient ObservableList<IPlayer> observablePlayers;
    private final int[] scores;

    //Physics object
    private final World world;
    
    //from field class
    private int size = 50;
    private int margin = 25;
    private float marginPhys = 3.75f;
    
         /**
     * Creates a new GameWorld object which keeps track of all the objects in
     * the game. Puck, 3 Pods, 3 Players,
     *
     * @param players An ArrayList of Player objects that will take part in this
     * game. Only the first 3 will be used.
     */
    public GameWorld(ArrayList<IPlayer> players) {
        world = new World(new Vec2(0.0f, 0.0f));
        createPuck();
        pods = new ArrayList<>();
        //Make sure only the first 3 players in the array get added
        this.players = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.players.add(players.get(i));
        }
        final ArrayList<Vec2> startPositions = getStartPositions();
        pods.add(new Pod(this, this.players.get(0), startPositions.get(0)));
        pods.add(new Pod(this, this.players.get(1), startPositions.get(1)));
        pods.add(new Pod(this, this.players.get(2), startPositions.get(2)));
        scores = new int[]{20, 20, 20};

        observablePlayers = FXCollections.observableArrayList(this.players);
        observablePods = FXCollections.observableArrayList(pods);
    }
    
    public void score(IPlayer scoredAgainst) {
        createPuck();
        int i = 0;
        try {

            while (puck.getTouched(i).getPlayer() == scoredAgainst) {
                i++;
            }
            //Add a score to whoever scored
            scores[findPlayerIndex(puck.getTouched(i).getPlayer().getName())] += 1;
            //Remove one point from who got scored against
            scores[findPlayerIndex(scoredAgainst.getName())] -= 1;
        } catch (NoSuchFieldException ex) {
            System.out.println("No such playername" + ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("No player other than the loser touched the pod... Score unchanged");
        }
        System.out.println(puck.getTouched(i).getPlayer().getName() + "scored!!!");
    }
    
    private void createPuck() {
        this.puck = new Puck(20, this);
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
        int index = -1;
        for (IPlayer p : players) {
            if (p.getName().equals(playerName)) {
                index = players.indexOf(p);
            }
        }
        return scores[index];
    }
    

    
    
    
    
    
    
    /**
     * Gets 3 coordinates of the 3 corners of the equilateral triangle that will
     * make up the playing field.
     *
     * @return An ArrayList with 3 Vec2 objects with the 3 points.
     */
    public ArrayList<Vec2> getFieldCorners() {
        ArrayList<Vec2> corners = new ArrayList<>();
        float s = size * 10f;
        corners.add(new Vec2(margin, s + margin));
        corners.add(new Vec2(s / 2 + margin, (float) ((s - s * sqrt(0.75)) + margin)));
        corners.add(new Vec2(s + margin, s + margin));
        return corners;
    }

    public final ArrayList<Vec2> getFieldCornersPhysx() {
        ArrayList<Vec2> corners = new ArrayList<>();
        corners.add(new Vec2(marginPhys, marginPhys));
        corners.add(new Vec2(size / 2 + marginPhys, (float) ((size * sqrt(0.75)) / 2 + marginPhys)));
        corners.add(new Vec2(size + marginPhys, marginPhys));
        return corners;
    }

    public Vec2 RotateVector2(Vec2 v, float angle) {
        Vec2 newVector;
        if (v.x > 10 && v.y > 10) {
            newVector = new Vec2((v.x - (size / 0.2f + margin)) * (float) Math.cos(angle) - (v.y - (size / 0.2f + margin)) * (float) Math.sin(angle) + (size / 0.2f + margin),
                    (v.x - (size / 0.2f + margin)) * (float) Math.sin(angle) + (v.y - (size / 0.2f + margin)) * (float) Math.cos(angle) + (size / 0.2f + margin));
        } else {
            newVector = new Vec2((v.x - (size / 2 + marginPhys)) * (float) Math.cos(angle) - (v.y - (size / 2 + marginPhys)) * (float) Math.sin(angle) + (size / 2 + marginPhys),
                    (v.x - (size / 2 + marginPhys)) * (float) Math.sin(angle) + (v.y - (size / 2 + marginPhys)) * (float) Math.cos(angle) + (size / 2 + marginPhys));
        }
        return newVector;
    }

    /**
     * Gets the size of the playing field
     *
     * @return
     */
    public int getSize() {
        return this.size;
    }

    public double getPodSize() {
        return size * 0.08;
    }

    public double getPuckSize() {
        return size * 0.04;
    }

    /**
     * Sets the size of the playing field
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Calculates the 4 coordinates of the rectangle that will represent a goal
     * on a playing field by taking the 2 points of the line which the goal has
     * to be placed on.
     *
     * @param x1 The x coordinate of the first point of the line
     * @param y1 The y coordinate of the first point of the line
     * @param x2 The x coordinate of the second point of the line
     * @param y2 The y coordinate of the second point of the line
     * @param sizeX The length the goal should be away from the line in X
     * direction
     * @param sizeY The length the goal should be away from the line in Y
     * direction
     * @return An ArrayList with 4 coordinates representing the corners of the
     * rectangle.
     */
    public ArrayList<Vec2> getGoalCorners(double x1, double y1, double x2, double y2, double sizeX, double sizeY) {
        ArrayList<Vec2> rectangleGoal = new ArrayList<>();
        double vx = x2 - x1; // x vector
        double vy = y2 - y1; // y vector
        double mag = sqrt(vx * vx + vy * vy); //magnitude (also known as length)
        vx /= mag; //normalize x vector
        vy /= mag; //normalize y vector

        rectangleGoal.add(new Vec2((int) ((float) x1 + vx * (mag * 0.3)), (int) ((float) y1 + vy * (mag * 0.3))));
        rectangleGoal.add(new Vec2((int) ((float) x1 + sizeX + vx * (mag * 0.3)), (int) ((float) y1 + sizeY + vy * (mag * 0.3))));
        rectangleGoal.add(new Vec2((int) ((float) x1 + sizeX + vx * (mag * 0.7)), (int) ((float) y1 + sizeY + vy * (mag * 0.7))));
        rectangleGoal.add(new Vec2((int) ((float) x1 + vx * (mag * 0.7)), (int) ((float) y1 + vy * (mag * 0.7))));
        return rectangleGoal;
    }

    private Vec2 getCenterOfLine(Vec2 a, Vec2 b) {
        return new Vec2((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    public ArrayList<Vec2> getStartPositions() {
        ArrayList<Vec2> positions = new ArrayList<>();
        ArrayList<Vec2> corners = getFieldCorners();
        Vec2 a = getCenterOfLine(corners.get(2), corners.get(0));
        a.x -= getPodSize() / 2;
        a.y -= getPodSize() / 2;
        Vec2 b = getCenterOfLine(corners.get(0), corners.get(1));
        b.x -= getPodSize() / 2;
        b.y -= getPodSize() / 2;
        Vec2 c = getCenterOfLine(corners.get(1), corners.get(2));
        c.x -= getPodSize() / 2;
        c.y -= getPodSize() / 2;
        a = RotateVector2(a, (float) Math.PI);
        b = RotateVector2(b, (float) Math.PI);
        c = RotateVector2(c, (float) Math.PI);
        positions.add(new Vec2(a.x / 10, a.y / 10));
        positions.add(new Vec2(b.x / 10, b.y / 10));
        positions.add(new Vec2(c.x / 10, c.y / 10));
        return positions;
    }
    
}


