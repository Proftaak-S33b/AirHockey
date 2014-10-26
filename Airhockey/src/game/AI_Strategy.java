/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 * Strategy design pattern to hold AI behaviour state.
 * @author Etienne
 */
public class AI_Strategy { 
//TODO - set strategies, refactor, complain about AI, refactor some more.
    
    public AI_Strategy(){
        
    }
    
    /**
     * Picks a different strategy/algorithm based on the difficulty of the AI.
     * @param diff 
     */
    public void setBehaviour(Difficulty diff){
        switch(diff){
            case EASY:
                System.out.println("AI set to EASY");
                break;
            case NORMAL:
                System.out.println("AI set to NORMAL");
                break;
            case HARD:
                System.out.println("AI set to HARD");
                break;
            default:
                System.out.println("AI strategy not set.");
                break;
        }
    }
}
