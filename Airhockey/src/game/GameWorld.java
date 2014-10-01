package game;

import java.util.List;
import javafx.collections.ObservableList;

public class GameWorld {

    private Puck puck;
    private Pod[] pods;
    private List<Player> players;
    private transient ObservableList<Player> observablePlayers;
    private int[] scores;
    private Field field;

    /**
     *
     * @param players
     */
    public GameWorld(Player[] players) {
        // TODO - implement GameWorld.GameWorld
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param playernr 1-3
     * @return
     */
    public Player getPlayer(int playernr) {
        // TODO - implement GameWorld.getPlayer
        throw new UnsupportedOperationException();
    }

}
