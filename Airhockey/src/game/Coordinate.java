package game;

public class Coordinate {

    public double x;
    public double y;

    /**
     * This class holds a x and y coordinate for use in calculations or drawing.
     * @param x
     * @param y
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString(){
        return x + ", " + y;
    }

}
