package game;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class GameWorld {

    //Game objects
    private final Puck puck;
    private final ArrayList<Pod> pods;
    private final transient ObservableList<Pod> observablePods;
    private final ArrayList<Player> players;
    private final transient ObservableList<Player> observablePlayers;
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
    public GameWorld(ArrayList<Player> players) {
        world = new World(new Vec2(0.0f, 0.0f));
        field = new Field(this, 500);
        puck = new Puck(this, 100000);
        pods = new ArrayList<>();
        //Make sure only the first 3 players in the array get added
        this.players = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.players.add(players.get(i));
        }
        pods.add(new Pod(this, this.players.get(0), field.getStartPositions().get(2)));
        pods.add(new Pod(this, this.players.get(1), field.getStartPositions().get(0)));
        pods.add(new Pod(this, this.players.get(2), field.getStartPositions().get(1)));
        scores = new int[]{20, 20, 20};

        observablePlayers = FXCollections.observableArrayList(this.players);
        observablePods = FXCollections.observableArrayList(pods);
    }

    /**
     * Get the player with the specified name. If no player with the specified
     * name is found, null is returned.
     *
     * @param name The name of the player
     * @return Returns the player object with the specified name.
     */
    public Player getPlayer(String name) {
        for (Player p : players) {
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
        for (Player p : players) {
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
