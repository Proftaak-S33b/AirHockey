package z_OLD_game;

public class Human implements Player {

    private final String name;
    private final String password;

    /**
     *
     * @param name
     * @param password
     */
    public Human(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
