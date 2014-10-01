package game;

import static java.lang.Math.*;
import java.util.ArrayList;

public class Field {

    private int size;

    private final int marge;

    /**
     * Creates a new field object with specified size.
     *
     * @param size
     */
    public Field(int size) {
        this.size = size;
        marge = 25;
    }

    /**
     * Gets 3 coordinates of the 3 corners of the equilateral triangle that will
     * make up the playing field.
     *
     * @return An ArrayList with 3 Coordinate objects with the 3 points.
     */
    public ArrayList<Coordinate> getFieldCorners() {
        ArrayList<Coordinate> corners = new ArrayList<>();
        corners.add(new Coordinate(marge, size + marge));
        corners.add(new Coordinate(size / 2 + marge, (size - size * sqrt(0.75)) + marge));
        corners.add(new Coordinate(size + marge, size + marge));
        return corners;
    }

    /**
     * Gets the size of the playing field
     *
     * @return
     */
    public int getSize() {
        return this.size;
    }
    
    public double getPodSize(){
        return size * 0.08;
    }
    
    public double getPuckSize(){
        return size * 0.04;
    }

    /**
     * Sets the size of the playing field
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Calculates the 4 coordinates of the rectangle that will represent a goal
     * on a playing field by taking the 2 points of the line which the goal has
     * to be placed on.
     *
     * @param x1 The x coordinate of the first point of the line
     * @param y1 The y coordinate of the first point of the line
     * @param x2 The x coordinate of the second point of the line
     * @param y2 The y coordinate of the second point of the line
     * @param sizeX The length the goal should be away from the line in X
     * direction
     * @param sizeY The length the goal should be away from the line in Y
     * direction
     * @return An ArrayList with 4 coordinates representing the corners of the
     * rectangle.
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

    private Coordinate getCenterOfLine(Coordinate a, Coordinate b) {
        return new Coordinate((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    public ArrayList<Coordinate> getStartPositions() {
        ArrayList<Coordinate> positions = new ArrayList<>();
        ArrayList<Coordinate> corners = getFieldCorners();
        Coordinate a = getCenterOfLine(corners.get(0), corners.get(1));
        a.x -= getPodSize()/2;
        a.y -= getPodSize()/2;
        Coordinate b = getCenterOfLine(corners.get(1), corners.get(2));
        b.x -= getPodSize()/2;
        b.y -= getPodSize()/2;
        Coordinate c = getCenterOfLine(corners.get(2), corners.get(0));
        c.x -= getPodSize()/2;
        c.y -= getPodSize()/2;
        positions.add(a);
        positions.add(b);
        positions.add(c);
        return positions;
    }

}
