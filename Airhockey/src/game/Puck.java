package game;

public class Puck {

    Pod[] pods;
    private Coordinate position;
    private int speed;
    private Coordinate direction;
    private Pod[] touched;

    /**
     *
     * @param speed
     */
    public Puck(int speed) {
        // TODO - implement Puck.Puck
        throw new UnsupportedOperationException();
    }

    public Coordinate getPosition() {
        return this.position;
    }

    /**
     *
     * @param position
     */
    public void move(Coordinate position) {
        // TODO - implement Puck.move
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Coordinate getDirection() {
        return this.direction;
    }

    public Pod getLastTouched() {
        // TODO - implement Puck.getLastTouched
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param touched
     */
    public void addTouched(Pod touched) {
        // TODO - implement Puck.addTouched
        throw new UnsupportedOperationException();
    }

}
