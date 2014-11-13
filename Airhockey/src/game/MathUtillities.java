/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;

/**
 *
 * @author maikel
 */
public class MathUtillities {
     //from field class
    private static int size = 50;
    private static int margin = 25;
    private static float marginPhys = 3.75f;
    
    
    /**
     * Gets 3 coordinates of the 3 corners of the equilateral triangle that will
     * make up the playing field.
     *
     * @return An ArrayList with 3 Vec2 objects with the 3 points.
     */
    public static ArrayList<Vec2> getFieldCorners() {
        ArrayList<Vec2> corners = new ArrayList<>();
        float s = size * 10f;
        corners.add(new Vec2(margin, s + margin));
        corners.add(new Vec2(s / 2 + margin, (float) ((s - s * sqrt(0.75)) + margin)));
        corners.add(new Vec2(s + margin, s + margin));
        return corners;
    }

    public static final ArrayList<Vec2> getFieldCornersPhysx() {
        ArrayList<Vec2> corners = new ArrayList<>();
        corners.add(new Vec2(marginPhys, marginPhys));
        corners.add(new Vec2(size / 2 + marginPhys, (float) ((size * sqrt(0.75)) / 2 + marginPhys)));
        corners.add(new Vec2(size + marginPhys, marginPhys));
        return corners;
    }

    public static Vec2 RotateVector2(Vec2 v, float angle) {
        Vec2 newVector;
        if (v.x > 10 && v.y > 10) {
            newVector = new Vec2((v.x - (size / 0.2f + margin)) * (float) Math.cos(angle) - (v.y - (size / 0.2f + margin)) * (float) Math.sin(angle) + (size / 0.2f + margin),
                    (v.x - (size / 0.2f + margin)) * (float) Math.sin(angle) + (v.y - (size / 0.2f + margin)) * (float) Math.cos(angle) + (size / 0.2f + margin));
        } else {
            newVector = new Vec2((v.x - (size / 2 + marginPhys)) * (float) Math.cos(angle) - (v.y - (size / 2 + marginPhys)) * (float) Math.sin(angle) + (size / 2 + marginPhys),
                    (v.x - (size / 2 + marginPhys)) * (float) Math.sin(angle) + (v.y - (size / 2 + marginPhys)) * (float) Math.cos(angle) + (size / 2 + marginPhys));
        }
        return newVector;
    }

    /**
     * Gets the size of the playing field
     *
     * @return
     */
    public static int getSize() {
        return size;
    }

    public static double getPodSize() {
        return size * 0.08;
    }

    public static double getPuckSize() {
        return size * 0.04;
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
        ArrayList<Vec2> rectangleGoal = new ArrayList<>();
        rectangleGoal.add(new Vec2(redXLeft, bottomY));
        rectangleGoal.add(new Vec2(redXRight, bottomY));
        rectangleGoal.add(new Vec2(blueXRight, topYtop));
        rectangleGoal.add(new Vec2(blueXLeft, topYbottom));
        rectangleGoal.add(new Vec2(greenXLeft, topYbottom));
        rectangleGoal.add(new Vec2(greenXRight, topYtop));
        return rectangleGoal;
    }
    private static final int bottomY = 5;
    private static final float topYtop = 29.248f;
    private static final float topYbottom = 15.392f;
    private static final int redXLeft = 17;
    private static final int redXRight = 33;
    private static final int blueXLeft = 11;
    private static final int blueXRight = 19;
    private static final int greenXRight = 31;
    private static final int greenXLeft = 39;
    

    private static Vec2 getCenterOfLine(Vec2 a, Vec2 b) {
        return new Vec2((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

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
}
