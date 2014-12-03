package game.AI;

/**
 * Indicates the difficulty setting of the singleplayer.
 * @author Etienne
 */
public enum Difficulty {

    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    private final String description;

    //This works? what is the plan with getDescription vs. toString?
    private Difficulty(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the enum.
     * @return a String with the description.
     */
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString()
    {
        return description;
    }
}
