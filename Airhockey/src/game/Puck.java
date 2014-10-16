package game;

import java.util.ArrayList;

public class Puck {

    private Coordinate position;
    private int speed;
    private final Coordinate direction;
    private final ArrayList<Pod> touched;

    /**
     *
     * @param speed
     */
    public Puck(int speed) {
        this.speed = speed;
        position = new Coordinate(Math.random() * 100 + 200, Math.random() * 100 + 200);
        direction = new Coordinate(Math.random() * 100 + 200, Math.random() * 100 + 200);
        touched = new ArrayList<>();
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
        return this.touched.get(0);
    }

    /**
     *
     * @param touched
     */
    public void addTouched(Pod touched) {
        this.touched.add(0, touched);
    }

}
