package game;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameWorld {

    private final Puck puck;
    private final ArrayList<Pod> pods;
    private final transient ObservableList<Pod> observablePods;
    private final ArrayList<Player> players;
    private final transient ObservableList<Player> observablePlayers;
    private final int[] scores;
    private final Field field;

    /**
     * Creates a new GameWorld object which keeps track of all the objects in
     * the game. Puck, 3 Pods, 3 Players,
     *
     * @param players An ArrayList of Player objects that will take part in this
     * game. Only the first 3 will be used.
     */
    public GameWorld(ArrayList<Player> players) {
        field = new Field(500);
        puck = new Puck(5);
        pods = new ArrayList<>();
        //Make sure only the first 3 players in the array get added
        this.players = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.players.add(players.get(i));
        }
        ArrayList<Coordinate> corners = field.getFieldCorners();
        pods.add(new Pod(this.players.get(0), corners.get(0)));
        pods.add(new Pod(this.players.get(1), corners.get(1)));
        pods.add(new Pod(this.players.get(2), corners.get(2)));
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
     * Get the pod which belongs to the player with the specified name. If no
     * player with the specified name is taking part in this game, null is
     * returned.
     *
     * @param playerName The name of the player the pod belongs to.
     * @return Returns the pod object that belongs to the player with the
     * specified name.
     */
    public Pod getPod(String playerName) {
        for (Pod p : pods) {
            if (p.getPlayer().getName().equals(playerName)) {
                return p;
            }
        }
        return null;
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
    
    public Field getField(){
        return field;
    }
}
