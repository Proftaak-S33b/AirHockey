/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.common.Vec2;

/**
 * All the Coordinates and calculates
 * @author maikel
 */
public class MathUtillities {

    //Coordinates of field.
    //         ^i 
    //      e/    \g
    //     f/      \h
    //     <___  ___>
    //     a   b c   d
    //
    //a = (5, 5)     e = (19, 29.248)    i = (25, 39.64)
    //b = (17, 5)    f = (11, 15.392)
    //c = (33, 5)    g = (31, 29.248)
    //d = (45, 5)    h = (39, 15.392)
    private static final int bottomY = 5;
    private static final float topYtop = 29.248f;
    private static final float topYbottom = 15.392f;
    private static final int redXLeft = 17;
    private static final int redXRight = 33;
    private static final int blueXLeft = 11;
    private static final int blueXRight = 19;
    private static final int greenXRight = 31;
    private static final int greenXLeft = 39;

    private static final int rightCornerX = 45;
    private static final int topCornerX = 25;
    private static final float topCornerY = 39.64f;

    //Size of the playing field
    private static final int fieldSize = 40;

    /**
     * Get the size of the pod
     * @return the podsize
     */
    public static double getPodSize() {
        return fieldSize * 0.08;
    }

    /**
     * Returns the distance from the center to the edge of the pod.
     * Helps the war on magic numbers.
     * @return a double with the radius.
     */
    public static double getPodRadius() {
	return getPodSize() / 2;
    }
    
    /**
     * Get the size of the puck
     * @return the pucksize
     */
    public static double getPuckSize() {
        return fieldSize * 0.04;
    }

    /**
     * Calculates a new vector to draw the shapes y = 50 - old y
     *
     * TODO ROTATE X
     * @param vector
     * @return a vector to draw a shape
     */
    /*public static Vec2 rotateVector(Vec2 vector) {
        float yVector = 50 - vector.y;
        return new Vec2(vector.x, yVector);
    }*/

    /**
     * Calculates the 4 coordinates of the rectangle that will represent a goal
     * on a playing field by taking the 2 points of the line which the goal has
     * to be placed on.
     *
     * @param corner
     * @return An ArrayList with 6 coordinates representing the corners of the
     * rectangle.
     */
    public static Vec2 getCoordinates(Corner corner) {
        Vec2 vector = new Vec2(0, 0);
        switch (corner) {
            case A:
                vector = new Vec2(bottomY, bottomY);
                break;
            case B:
                vector = new Vec2(redXLeft, bottomY);
                break;
            case C:
                vector = new Vec2(redXRight, bottomY);
                break;
            case D:
                vector = new Vec2(rightCornerX, bottomY);
                break;
            case E:
                vector = new Vec2(blueXRight, topYtop);
                break;
            case F:
                vector = new Vec2(blueXLeft, topYbottom);
                break;
            case G:
                vector = new Vec2(greenXLeft, topYbottom);
                break;
            case H:
                vector = new Vec2(greenXRight, topYtop);
                break;
            case I:
                vector = new Vec2(topCornerX, topCornerY);
                break;
        }
        return vector;
    }

    /**
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;^i<br>
     * &nbsp;e/&nbsp;&nbsp;&nbsp;\h<br>
     * f/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\g<br>
     *   /___&nbsp;&nbsp;___\<br>
     * a&nbsp;&nbsp;&nbsp;b&nbsp;c&nbsp;&nbsp;&nbsp;d<br>
     */
    public enum Corner {

        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I
    }

    /**
     * Gets the center of vector 'a' and 'b'
     *
     * @param a Vector a
     * @param b Vector b
     * @return The center of vector a and b
     */
    public static Vec2 getCenterOfLine(Vec2 a, Vec2 b) {
        //Is deze nodig?
        //Volgens mij niet
        //Bij wall kun je deze gebruiken en je moet ook nog de coordinaten getten uit getGoalCorners
        return new Vec2((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    /**
     * Get the startposition for the Pods
     * @return the startposition
     */
    //Deze heeft heel wat werk nodig
    public static ArrayList<Vec2> getStartPositions() {
        ArrayList<Vec2> positions = new ArrayList<>();
        ArrayList<Vec2> corners = new ArrayList<>();
        corners.add(getCoordinates(Corner.A));
        corners.add(getCoordinates(Corner.D));
        corners.add(getCoordinates(Corner.I));
        Vec2 a = getCenterOfLine(corners.get(0), corners.get(1));
        Vec2 b = getCenterOfLine(corners.get(2), corners.get(0));
        Vec2 c = getCenterOfLine(corners.get(1), corners.get(2));
        positions.add(new Vec2(a.x, a.y));
        positions.add(new Vec2(b.x, b.y));
        positions.add(new Vec2(c.x, c.y));
        return positions;
    }
    
    /**
     * Rotates the vector around a certaint point
     * Calculation used:
     * x'=(x-h)cos(T)-(y-k)sin(T)+h
     * y'=(x-h)sin(T)+(y-k)cos(T)+k
     * @param point The point that has to be rotated
     * @param center The point that is the center
     * @param angel The angle of which the point has to be rotated
     * @return The new vector
     */
    public static Vec2 rotateVectorAroundPoint(Vec2 point, Vec2 center, float angel)
    {
        float x = (float) ((point.x - center.x) * Math.cos(angel) - (point.y - center.y) * Math.sin(angel) + center.x);
        float y = (float) ((point.x - center.x) * Math.sin(angel) + (point.y - center.y) * Math.cos(angel) + center.y);
        return new Vec2(x,y);
    }
    
    /**
     * Returns a random integer between two given values (inclusive).
     * Works with negatives, unlike util.Random
     * <i>Work in progress.</i>
     * @param min the lower bound for the generator
     * @param max the upper bound for the generator - must be positive.
     * @return an integer with the generated number
     */
    public static int randomIntBetween(int min, int max) {
	// Generate between (negative)min and max. 
	// max is exclusive to nextInt(), so that gets corrected by 1.
	Random r = new Random();
	
	int num = r.nextInt((max + 1) + (Math.abs(min))) - min;
	
	boolean within_range = num >= min & num <= max ? true : false;
	
	// within range? change nothing. Else: recalculate.
	num = within_range ? num : randomIntBetween(min, max);
	
	return num;
    }
}