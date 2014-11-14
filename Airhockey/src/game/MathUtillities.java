/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;

/**
 *
 * @author maikel
 */
public class MathUtillities {

    //Coordinates of field.
    private static final int bottomY = 5;
    private static final float topYtop = 29.248f;
    private static final float topYbottom = 15.392f;
    private static final int redXLeft = 17;
    private static final int redXRight = 33;
    private static final int blueXLeft = 11;
    private static final int blueXRight = 19;
    private static final int greenXRight = 31;
    private static final int greenXLeft = 39;

    //Size of the playing field
    private static final int fieldSize = 40;

    /**
     *
     * @return
     */
    public static double getPodSize() {
        return fieldSize * 0.08;
    }

    /**
     *
     * @return
     */
    public static double getPuckSize() {
        return fieldSize * 0.04;
    }

    /**
     * Calculates the 4 coordinates of the rectangle that will represent a goal
     * on a playing field by taking the 2 points of the line which the goal has
     * to be placed on.
     *
     * @return An ArrayList with 6 coordinates representing the corners of the
     * rectangle.
     */
    public static ArrayList<Vec2> getGoalCorners() {
        //Hernoemen en misschien met enum doorgeven welk coordinaat je wilt getten?
        //Feedback over comments verwerken, = geef aan hoe we aan deze coordinaten komen
        ArrayList<Vec2> rectangleGoal = new ArrayList<>();
        rectangleGoal.add(new Vec2(redXLeft, bottomY));
        rectangleGoal.add(new Vec2(redXRight, bottomY));
        rectangleGoal.add(new Vec2(blueXRight, topYtop));
        rectangleGoal.add(new Vec2(blueXLeft, topYbottom));
        rectangleGoal.add(new Vec2(greenXLeft, topYbottom));
        rectangleGoal.add(new Vec2(greenXRight, topYtop));
        return rectangleGoal;
    }

    /**
     * Gets the center of vector 'a' and 'b'
     *
     * @param a Vector a
     * @param b Vector b
     * @return The center of vector a and b
     */
    private static Vec2 getCenterOfLine(Vec2 a, Vec2 b) {
        //Is deze nodig?
        return new Vec2((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    /**
     *
     * @return
     */
    //Deze heeft heel wat werk nodig
    /*
     public static ArrayList<Vec2> getStartPositions() {
     ArrayList<Vec2> positions = new ArrayList<>();
     ArrayList<Vec2> corners = getFieldCorners();
     Vec2 a = getCenterOfLine(corners.get(2), corners.get(0));
     a.x -= getPodSize() / 2;
     a.y -= getPodSize() / 2;
     Vec2 b = getCenterOfLine(corners.get(0), corners.get(1));
     b.x -= getPodSize() / 2;
     b.y -= getPodSize() / 2;
     Vec2 c = getCenterOfLine(corners.get(1), corners.get(2));
     c.x -= getPodSize() / 2;
     c.y -= getPodSize() / 2;
     a = RotateVector2(a, (float) Math.PI);
     b = RotateVector2(b, (float) Math.PI);
     c = RotateVector2(c, (float) Math.PI);
     positions.add(new Vec2(a.x / 10, a.y / 10));
     positions.add(new Vec2(b.x / 10, b.y / 10));
     positions.add(new Vec2(c.x / 10, c.y / 10));
     return positions;
     }
     */
}
