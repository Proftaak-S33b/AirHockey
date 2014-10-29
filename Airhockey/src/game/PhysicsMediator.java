/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import GUI.FXMLGameController;
import java.util.ArrayList;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Joris
 */
public class PhysicsMediator {

    private final GameWorld world;
    private final FXMLGameController controller;

    public PhysicsMediator(GameWorld world, FXMLGameController controller) {
        this.world = world;
        this.controller = controller;
    }
    
    public void addContactListener(ContactListener cl){
        world.getPhysWorld().setContactListener(cl);
    }

    public Vec2 getPuckPosition() {
        return rotateVector(world.getPuck().getPosition());
    }
    
    public int getPodIndex(String playerName) throws NoSuchFieldException{
        return world.findPlayerIndex(playerName);
    }

    public Vec2 getPodPosition(int podIndex) {
        return rotateVector(world.getPod(podIndex).getPosition());
    }

    /**
     *
     * @param podIndex
     * @return the new position of the pod
     */
    public Vec2 movePodLeft(int podIndex) {
            world.getPod(podIndex).moveLeft(podIndex);
            return rotateVector(world.getPod(podIndex).getPosition());
    }

    public Vec2 movePodRight(int podIndex) {
        world.getPod(podIndex).moveRight(podIndex);
        return rotateVector(world.getPod(podIndex).getPosition());
    }

    public ArrayList<Vec2> getFieldCorners() {
        return world.getField().getFieldCorners();
    }

    public int getFieldSize() {
        return world.getField().getSize();
    }

    public double getPodSize() {
        return world.getField().getPodSize();
    }

    public double getPuckSize() {
        return world.getField().getPuckSize();
    }

    public ArrayList<Vec2> getGoalCorners(double x1, double y1, double x2, double y2, double sizeX, double sizeY) {
        return world.getField().getGoalCorners(x1, y1, x2, y2, sizeX, sizeY);
    }
    
    public void step(float dt, int velocityIterations, int positionIterations){
        world.getPhysWorld().step(dt, velocityIterations, positionIterations);
    }
    

    private Vec2 rotateVector(Vec2 v) {
        return world.getField().RotateVector2(v, (float) Math.PI);
    }
    
    public void DEBUG_printPuckSpeed(){
        Vec2 v = world.getPuck().getVelocity().clone();
        System.out.println(v.normalize());
    }
}
