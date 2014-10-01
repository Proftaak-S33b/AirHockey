package game;

public class AI implements Player {

    private final String name;
    
    /**
     *
     * @param name
     */
    public AI(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
