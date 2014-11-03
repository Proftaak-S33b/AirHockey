package z_OLD_game;

public enum Difficulty {

    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    private final String omschr;

    private Difficulty(String omschr) {
        this.omschr = omschr;
    }

    public String getOmschr() {
        return omschr;
    }
    
    @Override
    public String toString()
    {
        return omschr;
    }
}
