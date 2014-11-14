/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z_OLD_game;

import GUI.GameView;
import java.util.ArrayList;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Joris
 */
public class PhysicsMediator {

    private final GameWorld world;
    private final GameView controller;

    public PhysicsMediator(GameWorld world, GameView controller) {
        this.world = world;
        this.controller = controller;
    }

    public void score(Player scoredAgainst) {
        world.score(scoredAgainst);
    }

    public void addContactListener(ContactListener cl) {
        world.getPhysWorld().setContactListener(cl);
    }

    public Vec2 getPuckPosition() {
        return rotateVector(
                new Vec2(world.getPuck().getPosition().x * 10,
                        world.getPuck().getPosition().y * 10));
    }

    public int getPodIndex(String playerName) throws NoSuchFieldException {
        return world.findPlayerIndex(playerName);
    }

    public Vec2 getPodPosition(int podIndex) {
        return rotateVector(
                new Vec2(world.getPod(podIndex).getPosition().x * 10,
                        world.getPod(podIndex).getPosition().y * 10));
    }

    /**
     *
     * @param podIndex
     * @return the new position of the pod
     */
    public Vec2 movePodLeft(int podIndex) {
        world.getPod(podIndex).moveLeft(podIndex);
        return getPodPosition(podIndex);
    }

    public Vec2 movePodRight(int podIndex) {
        world.getPod(podIndex).moveRight(podIndex);
        return getPodPosition(podIndex);
    }

    public ArrayList<Vec2> getFieldCorners() {
        return world.getField().getFieldCorners();
    }

    public double getPodSize() {
        return world.getField().getPodSize() * 10;
    }

    public double getPuckSize() {
        return world.getField().getPuckSize() * 10;
    }

    public ArrayList<Vec2> getGoalCorners(double x1, double y1, double x2, double y2, double sizeX, double sizeY) {
        return world.getField().getGoalCorners(x1, y1, x2, y2, sizeX, sizeY);
    }

    public void step(float dt, int velocityIterations, int positionIterations) {
        world.getPhysWorld().step(dt, velocityIterations, positionIterations);
    }

    private Vec2 rotateVector(Vec2 v) {
        return world.getField().RotateVector2(v, (float) Math.PI);
    }

    public Player hitGoal(Vec2 position) {
        //Check if red side was hit
        ArrayList<Vec2> corners = world.getField().getFieldCornersPhysx();

        float aX = corners.get(2).x;
        float aY = corners.get(2).y;
        float bX = corners.get(0).x;
        float bY = corners.get(0).y;
        corners = getGoalCorners(aX, aY, bX, bY, 0, 2.0);
        if (position.x > corners.get(2).x && position.x < corners.get(0).x) {
            return world.getPod(0).getPlayer();
        }
        //Check if blue side was hit
        corners = world.getField().getFieldCornersPhysx();

        aX = corners.get(0).x;
        aY = corners.get(0).y;
        bX = corners.get(1).x;
        bY = corners.get(1).y;
        corners = getGoalCorners(aX, aY, bX, bY, -1.8, -1.2);
        if ((position.x > corners.get(0).x && position.y > corners.get(0).y)
                && (position.x < corners.get(3).x && position.y < corners.get(3).y)) {
            return world.getPod(1).getPlayer();
        }

        //Check if green side was hit
        corners = world.getField().getFieldCornersPhysx();

        aX = corners.get(1).x;
        aY = corners.get(1).y;
        bX = corners.get(2).x;
        bY = corners.get(2).y;
        corners = getGoalCorners(aX, aY, bX, bY, 1.8, -1.2);
        if ((position.x > corners.get(0).x && position.y > corners.get(0).y)
                && (position.x < corners.get(3).x && position.y < corners.get(3).y)) {
            return world.getPod(2).getPlayer();
        }

        //No goal hit
        return null;
    }
}
