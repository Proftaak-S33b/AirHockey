package game.AI;

import game.GameWorld;
import game.Pod;
import game.MathUtillities.*;
import static game.MathUtillities.getCoordinates;
import static game.MathUtillities.getPodSize;

/**
 * Strategy design pattern to hold AI behaviour state.
 * @author Etienne
 */
public class Strategy implements IStrategy { 
    
    AI ai;
    GameWorld gameworld;
    Pod pod;
    Difficulty difficulty;
    int index;	    // which pod the ai has
    int range;	    // how far the ai can see
    int deadzone;   // how close the ai can get
    int offset;	    // how often the ai fails
    
    /**    
     *   TODO - 
     *   1. set strategies 
     *   2. refactor
     *   3. complain about AI-
     *   4. ???
     *   5. <s>more refactoring</s> profit!
     */
    public Strategy(){
        try {
	    pod = gameworld.getPod(gameworld.findPlayerIndex(ai.getName()));
	    index = gameworld.findPlayerIndex(ai.getName());
	} catch (Exception e) {
	}
    }
    
    
    /**
     * Picks a different strategy/algorithm based on the difficulty of the AI.
     * @param diff 
     */
    public void setBehaviour(){
        switch(difficulty){
            case EASY:
                System.out.println("AI set to EASY");
                // Insert difficulty-specific logic here..
                break;
            case NORMAL:
                System.out.println("AI set to NORMAL");
                // and here...
                break;
            case HARD:
                System.out.println("AI set to HARD");
                // oh, and here.
                break;
        }
    }
    
    /**
     * Part of slowly phasing out the AI from the Controller to the AI classes.
     * Method content should be moved.
     */
    private void CalculateMovement() {
        // temp | note-to-self: avoid hardcoding     
        float podY = pod.getPosition().y; // AI #1
        float puckY = gameworld.getPuck().getPosition().y;

        // Measure distance - whether y is lower or higher, the distance will 
        // always be a positive number.
        float distance = puckY - podY;
	
	//ternary shorthand:
	//distance = distance < 0 ? -distance : distance;
        if (distance < 0) {
            distance -= distance + distance;
        }	

        // Create deadzone to prevent flickering.
        //When in doubt, set to 25. jus werks.
        float personalspace = 0;
        if (difficulty == Difficulty.EASY) {
            personalspace = 5;
        } else if (difficulty == Difficulty.NORMAL) {
            personalspace = 3;
        } else if (difficulty == Difficulty.HARD) {
            personalspace = 1;
        }

        // Where is the puck?
        if (puckY < podY) {
            // Does the AI respect the puck's personal space?
            if (distance > personalspace) {
                moveUp("BLUE");
            }

        }

        // Where is the puck?
        if (puckY > podY) {
            // Does the AI respect the puck's personal space?
            if (distance > personalspace) {
                moveDown("BLUE");
            }
        }
    }

    /**
     * Moves the AI up from player viewpoint.
     *
     * @param color The color of the pod to move. "BLUE" or "GREEN" allowed
     */
    private void moveUp(String color) {
        if (color.equals("BLUE")) {
            if (pod.getPosition().y >
		    getCoordinates(Corner.F).y + getPodSize() / 2) {
                pod.moveLeft(1);
            }
        }
    }

    /**
     * Moves the AI down from player viewpoint.
     *
     * @param color The color of the pod to move. "BLUE" or "GREEN" allowed
     */
    private void moveDown(String color) {
        if (color.equals("BLUE")) {
            if (pod.getPosition().y <
		    getCoordinates(Corner.E).y - getPodSize() / 2) {
		pod.moveRight(1);
            }
        }
    }

    @Override
    public void move() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    
    
    
    
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * 
     *//*
    class EASY{
    @Override
	public void move() {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
    }
    */
    /**
     * 
     *//*
    class NORMAL{
    @Override
	public void move() {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
    }
    */
    /**
     * 
     *//*
    class HARD{
    @Override
	public void move() {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
    }
*/
}