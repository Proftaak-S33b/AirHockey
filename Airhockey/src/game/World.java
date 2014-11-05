/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Joris
 */
public class World {    
    //Game objects
    private Puck puck;
    private final ArrayList<Pod> pods;
    private final transient ObservableList<Pod> observablePods;
    private final ArrayList<IPlayer> players;
    private final transient ObservableList<IPlayer> observablePlayers;
    private final int[] scores;
    private final Field field;

    //Physics object
    private final World world;
    
         /**
     * Creates a new GameWorld object which keeps track of all the objects in
     * the game. Puck, 3 Pods, 3 Players,
     *
     * @param players An ArrayList of Player objects that will take part in this
     * game. Only the first 3 will be used.
     */
    public World(ArrayList<IPlayer> players) {
        world = new World(new Vec2(0.0f, 0.0f));
        field = new Field(this, 50);
        createPuck();
        pods = new ArrayList<>();
        //Make sure only the first 3 players in the array get added
        this.players = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.players.add(players.get(i));
        }
        final ArrayList<Vec2> startPositions = field.getStartPositions();
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
        this.puck = new Puck(this, 20);
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
    
    public Field getField() {
        return field;
    }
    
}


