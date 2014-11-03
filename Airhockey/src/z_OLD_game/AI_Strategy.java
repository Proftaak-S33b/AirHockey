/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z_OLD_game;

/**
 * Strategy design pattern to hold AI behaviour state.
 * @author Etienne
 */
public class AI_Strategy { 
    /*    
     *   TODO - 
     *   1. set strategies 
     *   2. refactor
     *   3. complain about AI
     *   4. ???
     *   5. more refactoring
     */
    
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
            default:
                System.out.println("AI strategy not set.");
                // this will never be reached but should stay to prevent errors.
                break;
        }
    }
    
    class AI_Strategy_EASY{
        
    }
    
    class AI_Strategy_NORMAL{
        
    }
    
    class AI_Strategy_HARD{
        
    }
    
}
