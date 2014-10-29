package game;

import static java.lang.Math.*;
import java.util.ArrayList;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Field {

    //Class variables
    private int size;
    private int margin;
    private float marginPhys;

    //Physics objects
    private final Body bodyRedSide;
    private final Body bodyBlueSide;
    private final Body bodyGreenSide;

    /**
     * Creates a new field object with specified size.
     *
     * @param world
     * @param size
     */
    public Field(GameWorld world, int size) {
        this.size = size;
        margin = 25;
        marginPhys = 2.5f;

        /**
         * **Red side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set(getFieldCornersPhysx().get(0).x,
                    getFieldCornersPhysx().get(0).y);
            bdSide.type = BodyType.STATIC;
            bdSide.angle = 0;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(50, 0),
                    new Vec2(0, 0));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyRedSide = world.getPhysWorld().createBody(bdSide);
            bodyRedSide.createFixture(fdSide);
        }
        /**
         * **Blue side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set(getFieldCornersPhysx().get(0).x,
                    getFieldCornersPhysx().get(1).y);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(25.0f, 43.3f / 2), new Vec2(0, -43.3f / 2));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyBlueSide = world.getPhysWorld().createBody(bdSide);
            bodyBlueSide.createFixture(fdSide);
        }
        /**
         * **Green side**
         */
        //body definition
        {
            BodyDef bdSide = new BodyDef();
            bdSide.position.set(getFieldCornersPhysx().get(1).x,
                    getFieldCornersPhysx().get(1).y);
            bdSide.type = BodyType.STATIC;
            //define shape of the body.
            EdgeShape esSide = new EdgeShape();
            esSide.set(new Vec2(0, 43.3f / 2),
                    new Vec2(25.0f, -43.3f / 2));
            //define fixture of the body.
            FixtureDef fdSide = new FixtureDef();
            fdSide.shape = esSide;
            fdSide.friction = 0.3f;
            fdSide.restitution = 1f;
            //create the body and add fixture to it
            bodyGreenSide = world.getPhysWorld().createBody(bdSide);
            bodyGreenSide.createFixture(fdSide);
        }
    }

    /**
     * Gets 3 coordinates of the 3 corners of the equilateral triangle that will
     * make up the playing field.
     *
     * @return An ArrayList with 3 Vec2 objects with the 3 points.
     */
    public ArrayList<Vec2> getFieldCorners() {
        ArrayList<Vec2> corners = new ArrayList<>();
        float s = size * 10f;
        corners.add(new Vec2(margin, s + margin));
        corners.add(new Vec2(s / 2 + margin, (float) ((s - s * sqrt(0.75)) + margin)));
        corners.add(new Vec2(s + margin, s + margin));
        return corners;
    }

    public final ArrayList<Vec2> getFieldCornersPhysx() {
        ArrayList<Vec2> corners = new ArrayList<>();
        corners.add(new Vec2(marginPhys, marginPhys));
        corners.add(new Vec2(size / 2 + marginPhys, (float) ((size * sqrt(0.75)) / 2 + marginPhys)));
        corners.add(new Vec2(size + marginPhys, marginPhys));
        return corners;
    }

    public Vec2 RotateVector2(Vec2 v, float angle) {
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
    public int getSize() {
        return this.size;
    }

    public double getPodSize() {
        return size * 0.08;
    }

    public double getPuckSize() {
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
    public ArrayList<Vec2> getGoalCorners(double x1, double y1, double x2, double y2, double sizeX, double sizeY) {
        ArrayList<Vec2> rectangleGoal = new ArrayList<>();
        double vx = x2 - x1; // x vector
        double vy = y2 - y1; // y vector
        double mag = sqrt(vx * vx + vy * vy); //magnitude (also known as length)
        vx /= mag; //normalize x vector
        vy /= mag; //normalize y vector

        rectangleGoal.add(new Vec2((int) ((float) x1 + vx * (mag * 0.3)), (int) ((float) y1 + vy * (mag * 0.3))));
        rectangleGoal.add(new Vec2((int) ((float) x1 + sizeX + vx * (mag * 0.3)), (int) ((float) y1 + sizeY + vy * (mag * 0.3))));
        rectangleGoal.add(new Vec2((int) ((float) x1 + sizeX + vx * (mag * 0.7)), (int) ((float) y1 + sizeY + vy * (mag * 0.7))));
        rectangleGoal.add(new Vec2((int) ((float) x1 + vx * (mag * 0.7)), (int) ((float) y1 + vy * (mag * 0.7))));
        return rectangleGoal;
    }

    private Vec2 getCenterOfLine(Vec2 a, Vec2 b) {
        return new Vec2((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    public ArrayList<Vec2> getStartPositions() {
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
