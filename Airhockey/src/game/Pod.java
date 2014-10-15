package game;

public class Pod {

    private final Player player;
    private Coordinate position;

    /**
     *
     * @param player
     * @param position
     */
    public Pod(Player player, Coordinate position) {
        this.player = player;
        this.position = position;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Coordinate getPosition() {
        return this.position;
    }

    /**
     *
     * @param position
     */
    public void move(Coordinate position) {
        this.position = position;
    }

}
