package game.AI;

/**
 * Strategy design pattern to hold AI behaviour state.
 * @author Etienne
 */
public class Strategy implements IStrategy { 
    
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

    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}