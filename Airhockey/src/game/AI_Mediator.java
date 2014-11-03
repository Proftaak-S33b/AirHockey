package game;

import GUI.FXMLGameController;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import org.jbox2d.common.Vec2;

/**
 * Mediator design pattern for interaction of Puck > AI > Controller.
 * @author Etienne
 */
public class AI_Mediator extends Observable implements Observer{
    //TODO: code refactoring and dependency resolving
    
    private Puck puck; 
    private ArrayList<AI> AI_List = new ArrayList<>();
    private FXMLGameController controller;
    private PhysicsMediator pm;
    
    public AI_Mediator(FXMLGameController controller, PhysicsMediator pm, ArrayList<Player> players, Puck puck){
        this.puck = puck;        
        this.controller = controller;
        this.pm = pm;
        //Initializes the AI.
        initAI(players);
    }
    
    /**
     * Filters AI from Human in Playerlist.
     * @param players Player interfaces.
     */
    public void initAI(ArrayList<Player> players){
        for (Player p : players){
            if (p instanceof AI) {
                AI ai = (AI)p;
                AI_List.add(ai);
                puck.addObserver(ai);
                ai.addObserver(this);
            }
        }
    }

    /**
     * Clears the list of observers.
     */
    public void clearPuckObservers(){
        puck.deleteObservers();
    }
    
    //These are not yet fully implemented.
    
    /**
     * TODO - Part of slowly phasing out the AI from the Controller to the AI classes.
     * @param goalCoordinates a list of coordinates for the pods to stay within.
     */
    private void AI_CalculateMovement(ArrayList<Vec2> goalCoordinates){
        
        // temp | note-to-self: avoid hardcoding     
        float p1Y = pm.getPodPosition(1).y; // AI #1     
        float p2Y = pm.getPodPosition(2).y; // AI #2
        float puckY = pm.getPuckPosition().y;
        
        /* Measure distance - whether y is lower or higher, the distance will 
         * always be a positive number.
         */
        float distance = puckY - p2Y;
        if (distance < 0) {
            distance -= distance + distance;
        }
        
        // Create deadzone to prevent flickering.
        //When in doubt, set to 25. jus werks.
        float personalspace = (float)pm.getPodSize() / 2;
        
        // Where is the puck?
        if (puckY < p1Y & puckY < p2Y) {
            // Does the AI respect the puck's personal space?                
            if (distance > personalspace) {
                AI_moveUp(goalCoordinates);
            }
            
        }
        if (puckY > p1Y & puckY > p2Y) {
            // Does the AI respect the puck's personal space?                
            if (distance > personalspace) {
                AI_moveDown(goalCoordinates); 
            }                
        }
        
        //System.out.println("Distance: " + distance + ", puck.y: " + puckY + ", pod.y: " + p2Y);        
    }
    
    /**
     * TODO - Moves the AI up from player viewpoints.
     * @param goalCoordinates 
     */
    private void AI_moveUp(ArrayList<Vec2> goalCoordinates){
            if (pm.getPodPosition(1).y > goalCoordinates.get(2).y + pm.getPodSize() / 2) {
                pm.movePodLeft(1);
                pm.movePodRight(2);
            }
    }
    
    /**
     * TODO - Moves the AI down from player viewpoint.
     * @param goalCoordinates 
     */
    private void AI_moveDown(ArrayList<Vec2> goalCoordinates){
            if (pm.getPodPosition(1).y < goalCoordinates.get(0).y - pm.getPodSize()) {
                pm.movePodLeft(2);
                pm.movePodRight(1);
            }
    }
    
    @Override
    public void update(Observable o, Object arg) {        
        //AI has received new coords and wants to move.
        System.out.println("MEDIATOR UPDATED.");
        this.AI_List = (ArrayList<AI>)arg;
        
        controller.updatePodPositions((Object)AI_List);
    }
}