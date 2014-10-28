package game;

import GUI.FXMLGameController;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

/**
 * Mediator design pattern for interaction of Puck > AI > Controller.
 * @author Etienne
 */
public class AI_Mediator extends Observable implements Observer{
    //TODO: code refactoring and dependency resolving
    
    private Puck puck; 
    private ArrayList<AI> AI_List = new ArrayList<>();
    private FXMLGameController controller;
    
    public AI_Mediator(FXMLGameController controller, ArrayList<Player> players, Puck puck){
        this.puck = puck;        
        this.controller = controller;
        
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
    
    @Override
    public void update(Observable o, Object arg) {        
        //AI has received new coords and wants to move.
        System.out.println("MEDIATOR UPDATED.");
        this.AI_List = (ArrayList<AI>)arg;
        
        controller.updatePodPositions((Object)AI_List);
    }
}