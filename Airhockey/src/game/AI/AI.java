package game.AI;

import game.GameWorld;
import game.MathUtillities.Corner;
import static game.MathUtillities.getCoordinates;
import static game.MathUtillities.getPodRadius;
import static game.MathUtillities.randomIntBetween;
import game.Pod;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import networking.IPlayer;
import java.util.Observable;
import java.util.Observer;
import org.jbox2d.common.Vec2;

/**
 * Artificial Intelligence for computer-driven opponents.
 * @author Etienne
 */
public class AI extends Observable implements IPlayer, Observer{
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * A final string with the name of the AI opponent.
     */
    private final String name;
    
    /**
     * the score of the AI.
     * Default score is 20 and declines as the game progresses.
     */
    private int score;
    
    /**
     * The rating of the AI. 
     * The rating is typically based on the results in previous games.
     * For the Ai however this is pre-determined.
     */
    private int rating;
    
    /**
     * The difficulty of the AI.
     * Possible difficulties: EASY, NORMAL, HARD.
     */
    private Difficulty difficulty;
    
    /**
     * Indicates whether the AI is gay or straight,
     * or might represent the political view.
     * I have no idea.
     */
    private Vec2 direction;
    
    /**
     * Index of the pod to make searching and movement easier.
     */
    private int podindex;
    
    /**
     * Pod object so the AI can now actually do what she was programmed for;
     * Control her pod.
     */
    private Pod pod;
    
    /**
     * GameWorld object to make dependencies less and dynamize more.
     */
    private GameWorld gameworld;
    
    /**
     * Strategy pattern to change movement according to difficulty.
     */
    private final IStrategy strategy;
    
    /**
     * An array of potential responses for in the chat.
     */
    private String[] messages = new String[]
    { 
	// RSVP: made this a little more human-friendly.
	//	 Also removed some 'internet inside-jokes'.
	
	// At start
	"hey, what's up",
	"hi! :) I'm a girl btw",
	"Alright, Let's do this",
	"May the best win!",
	"Just surrender already!",
	// At end, lost
	"omfg hax",
	"why you do dis",
	// At end, won
	"gg no re",
	"gg wp",
	"wow such win",
	// When got scored	
	"seems legit",
	"Player = OP, nerf pls",
	"noooooo!",
	"haha, oh wow",
	"nice 1 dude",
	"fite me irl m8",
	"much hax, very legit",
	// When scores
	"W00T!!!111einz",
	"ayy lmao",
	"OHH BABY A TRIPLE",
	"Get noscoped!!1!1!one",
	"Faze clan pls",
	"get rekt",
	"Why didn't you listen?",
	"#rekt"
    };
    
    //<editor-fold defaultstate="collapsed" desc="huge hashmapcopy of messages">
    /** 
     * A key-value array with all the messages. 
     * Same as <i>AI.messages</i>, but with added suppport for event-specific
     * messages in a dynamic way.
     * e.g. You can add and remove all you want without fucking around with 
     * indexes to keep track of every string's specific usage.
     * For adding events, see <i>AI.chatEvent</i>. 
     * For using these, see <i>AI.chat(chatEvent whathappened)</i>.
     */
    private HashMap<chatEvent, String> messages2 = new HashMap(){{
	// At start
	put(chatEvent.AT_START, "hey, what's up");
	put(chatEvent.AT_START, "hi! :) I'm a girl btw");
	put(chatEvent.AT_START, "Alright, Let's do this");
	put(chatEvent.AT_START, "May the best win!");
	put(chatEvent.AT_START, "Just surrender already!");
	// At end, lost
	put(chatEvent.AT_END_LOST, "omfg hax");
	put(chatEvent.AT_END_LOST, "i bet you play this all day");
	// At end, won
	put(chatEvent.AT_END_WON, "gg no re");
	put(chatEvent.AT_END_WON, "gg wp");
	put(chatEvent.AT_END_WON, "wow such win");
	// When got scored	;
	put(chatEvent.ON_SCORED_AGAINST, "seems legit");
	put(chatEvent.ON_SCORED_AGAINST, "Player = OP, nerf pls");
	put(chatEvent.ON_SCORED_AGAINST, "noooooo!");
	put(chatEvent.ON_SCORED_AGAINST, "haha, oh wow");
	put(chatEvent.ON_SCORED_AGAINST, "nice 1 dude");
	put(chatEvent.ON_SCORED_AGAINST, "fite me irl m8");
	put(chatEvent.ON_SCORED_AGAINST, "much hax, very legit");
	// When scores;
	put(chatEvent.ON_SCORED, "W00T!!!111einz");
	put(chatEvent.ON_SCORED, "ayy lmao");
	put(chatEvent.ON_SCORED, "OHH BABY A TRIPLE");
	put(chatEvent.ON_SCORED, "get noscoped!!1!1!one");
	put(chatEvent.ON_SCORED, "Faze clan pls");
	put(chatEvent.ON_SCORED, "GET REKT");
	put(chatEvent.ON_SCORED, "Why didn't you listen?");
	put(chatEvent.ON_SCORED, "#rekt");
    }};
    //</editor-fold>
    
    /**
     * Events for the AI to respond to.
     * Used to indicate which type of message to generate from chat().
     */
    public static enum chatEvent{
	AT_START,
	AT_END_WON,
	AT_END_LOST,
	ON_SCORED, // when AI scores.
	ON_SCORED_AGAINST, // when another player scores on this AI.
    }
    
    //</editor-fold>
    
    /**
     * Creates a new AI object with a given name and score.     
     * @param name String representing the given name of the AI.
     * @param score the starting score of the AI.
     */
    public AI(String name, int score/*, GameWorld gameworld*/) /*throws NoSuchFieldException*/ {
        this.name = name;
        this.score = score;
	this.gameworld = null;//gameworld;	
	this.podindex = 0;//gameworld.findPlayerIndex(name);	
	this.pod = null;//gameworld.getPod(podindex);
	this.strategy = null;//setStrategy();
    }

    /**
     * Creates a new AI object with a given name.
     * @param name String representing the given name of the AI.
     * @param gameworld
     * @throws java.lang.NoSuchFieldException
     */
    public AI(String name, GameWorld gameworld) throws NoSuchFieldException {
        this.name = name;
        this.score = 20;
	this.gameworld = gameworld;
	this.podindex = gameworld.findPlayerIndex(name);	
	this.pod = gameworld.getPod(podindex);	
	this.strategy = setStrategy();
    }
    
    /**
     * Returns the direction of the AI.
     * @return a vector(Vec2) with the coordinates of where the AI is headed.
     */
    public Vec2 getDirection(){
        return this.direction;
    }
    
    /**
     * Sets the score.
     * @param score
     */
    public void setScore(int score) {
	this.score = score;
    }

    /**
     * Returns the associated strategy class for it's difficulty.
     * @return the right strategy class for the given difficulty. 
     * Returns null if it couldn't find one.
     */
    private IStrategy setStrategy(){
	switch(difficulty)
	{
	    case EASY:
		return null;
	    case NORMAL:
		return null;
	    case HARD:
		return null;
	}
	return null;
    }
    
    /**
     * Determine if movement is needed, then move from Pod.
     */
    public void think(){
	//int range = 0;        // |Unused
	//int deadzone = 0;	// |	
		
	float podY = pod.getPosition().y;
	float puckY = gameworld.getPuck().getPosition().y; //observe puck?
	float puckX = gameworld.getPuck().getPosition().x;
	
	/* Measure distance - the distance will 
	 * always be a positive number thanks to abs().
	 */
	float distance = Math.abs(puckY - podY);
	
	// Create deadzone to prevent flickering.
	//When in doubt, set to 25. jus werks.
	float personalspace = 5;

	// Does the AI respect the puck's personal space?
	if (distance > personalspace) {
	    move();
	}
    }
    
    /**
     * Chats with the other players following pre-generated messages.
     * @return a String containing the message from the array.
     */
    public String chat(){
	int message = randomIntBetween(0, messages.length);
	
	return messages[message];
    }
    
    /**
     * Chats with the other players following pre-generated messages.
     * @param whathappened a chatEvent associated to the message.
     * @return a String containing the message from the array.
     */
    public String chat(chatEvent whathappened){
	// Make temporary list to hold the right pairs.
	ArrayList<String> potentials = new ArrayList();
	
	// iterate through messages with, add ones with right key.
	for(chatEvent key : messages2.keySet()){
	    if (key.equals(whathappened)) {
		potentials.add(messages2.get(key));
	    }
	}
	
	// Get random message from potentials, correct for zero-indexing.
	int message = randomIntBetween(0, potentials.size() - 1);
	
	return potentials.get(message);
    }
       
    //<editor-fold defaultstate="collapsed" desc="movement">    
    
    /**
     * Determines the right movement for the AI dynamically.
     */
    private void move(){
	// uppers:  bF gG   | lowers:	bE gH
	// up:	    > +	    | down:	< -
	
	// measure pods location.
	int x = (int) pod.getPosition().x;
	
	// Set coordinates to find out where the pod is.
	int upperleft = (int) getCoordinates(Corner.E).x;
	int lowerleft = (int) getCoordinates(Corner.F).x;
	int upperright = (int) getCoordinates(Corner.G).x;
	int lowerright = (int) getCoordinates(Corner.H).x;
		
	// look at x-coordinate to see which goal the AI is defending.
	Corner upper = x > lowerleft & x < upperleft ? Corner.E : Corner.G;
	// look at upper to determine lower
	Corner lower = upper == Corner.E ? Corner.F : Corner.H;
	// Set color too.
	Color c = lower == Corner.F ? Color.BLUE : Color.GREEN;
	
	// Measure if moving is physically possible.
	boolean moveable_up = pod.getPosition().y < getCoordinates(upper).y +
		getPodRadius();
	boolean moveable_down = pod.getPosition().y > getCoordinates(lower).y -
		getPodRadius();
		
	// Move if applicable.
	if (moveable_up)
	    moveUp(c.toString());
	if (moveable_down)
	    moveDown(c.toString());
    }
    
    /**
     * Moves the AI up from player viewpoint.
     * @param color The color of the pod to move. "BLUE" or "GREEN" allowed
     */
    private void moveUp(String color) {
	if (color.equals("BLUE")) {
		pod.moveLeft(podindex);
	}
	if (color.equals("GREEN")) {
		pod.moveRight(podindex);	    
	}
    }
    
    /**
     * Moves the AI down from player viewpoint.
     * @param color The color of the pod to move. "BLUE" or "GREEN" allowed
     */
    private void moveDown(String color) {
	if (color.equals("BLUE")) {
		pod.moveRight(podindex);
	}
	if (color.equals("GREEN")) {
		pod.moveLeft(podindex);
	}
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="// Overrides //////////////////////////////////////////////////////////////">
    
    /**
     * AI has no need to get ranked.
     * @param ranking 
     */
    @Override
    public void setRanking(int ranking) {
        System.out.println("Set ranking of AI.");
    }
    
    /**
     * Return the name of the AI opponent.
     * @return a String with the name of tha AI.
     */        
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the ranking of the player.
     * @return an int with the score.
     */
    @Override
    public int getRanking() {
        return this.score;
    }    
    
    /**
     * Currently updates the AI with the direction.
     * @param o The observable flagging the update.
     * @param arg the argument passed by the observable. Probably needs casting.
     */
    @Override
    public void update(Observable o, Object arg) {
        this.direction = (Vec2)arg;
	setChanged();
        notifyObservers(direction);
        clearChanged();
	
	System.out.println("Updated AI: "+ this.direction.x + ", " + this.direction.y);        
    }    
     
    /**
     * Sets the ranking for this AI.
     * @param scoreBool a boolean indicating if the AI scored.
     * true = score +1 , false = score -1
     */
    @Override
    public void changeRanking(Boolean scoreBool) {
	if (scoreBool) {
	    score++;
	} else {
	    score--;
	}
    }
    
    //</editor-fold>
}
