package z_OLD_game;

public class Administrator {

    private Administrator singleton;
    private Player player;

    /**
     *
     * @param player
     */
    private Administrator(Player player) {
        // TODO - implement Administrator.Administrator
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param player
     * @return returns the Administrator instance
     */
    public static Administrator getInstance(Player player) {
        // TODO - implement Administrator.getInstance
        throw new UnsupportedOperationException();
    }

    public Player getPlayer() {
        return this.player;
    }

}
