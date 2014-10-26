package game;

import org.jbox2d.common.Vec2;

public class Goal {

    private Vec2 position;
    private int size;
    private Player player;

    /**
     *
     * @param position
     * @param size
     * @param player
     */
    public Goal(Vec2 position, int size, Player player) {
        // TODO - implement Goal.Goal
        throw new UnsupportedOperationException();
    }

    public Vec2 getPosition() {
        return this.position;
    }

    public int getSize() {
        return this.size;
    }

    public Player getPlayer() {
        return this.player;
    }

}
