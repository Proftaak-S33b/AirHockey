package game;

public class Goal {

    private Coordinate position;
    private int size;
    private Player player;

    /**
     *
     * @param position
     * @param size
     * @param player
     */
    public Goal(Coordinate position, int size, Player player) {
        // TODO - implement Goal.Goal
        throw new UnsupportedOperationException();
    }

    public Coordinate getPosition() {
        return this.position;
    }

    public int getSize() {
        return this.size;
    }

    public Player getPlayer() {
        return this.player;
    }

}
