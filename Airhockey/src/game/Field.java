package game;

import static java.lang.Math.*;
import java.util.ArrayList;

public class Field {

    private int size;

    /**
     * Creates a new field object with specified size.
     * @param size
     */
    public Field(int size) {
        this.size = size;
    }

    /**
     * Gets the size of the playing field
     * @return 
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Sets the size of the playing field
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * Calculates the 4 coordinates of the rectangle that will represent a goal 
     * on a playing field by taking the 2 points of the line which the goal has to be placed on.
     * @param x1 The x coordinate of the first point of the line
     * @param y1
     * @param x2
     * @param y2
     * @param sizeX
     * @param sizeY
     * @return 
     */
    public ArrayList<Coordinate> getGoalCorners(double x1, double y1, double x2, double y2, double sizeX, double sizeY) {
        ArrayList<Coordinate> rectangleGoal = new ArrayList<>();
        double vx = x2 - x1; // x vector
        double vy = y2 - y1; // y vector
        double mag = sqrt(vx * vx + vy * vy); //magnitude (also known as length)
        vx /= mag; //normalize x vector
        vy /= mag; //normalize y vector

        rectangleGoal.add(new Coordinate((int) ((float) x1 + vx * (mag * 0.3)), (int) ((float) y1 + vy * (mag * 0.3))));
        rectangleGoal.add(new Coordinate((int) ((float) x1 + sizeX + vx * (mag * 0.3)), (int) ((float) y1 + sizeY + vy * (mag * 0.3))));
        rectangleGoal.add(new Coordinate((int) ((float) x1 + sizeX + vx * (mag * 0.7)), (int) ((float) y1 + sizeY + vy * (mag * 0.7))));
        rectangleGoal.add(new Coordinate((int) ((float) x1 + vx * (mag * 0.7)), (int) ((float) y1 + vy * (mag * 0.7))));
        return rectangleGoal;
    }

}
