/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GUI.GameView;
import game.AI.Difficulty;
import game.GameWorld;
import game.Goal;
import game.MathUtillities;
import game.Pod;
import game.Puck;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import networking.IPlayer;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.collision.shapes.CircleShape;

/**
 * Controller for managing game logic
 *
 * @author Maikel
 */
public class GameManager implements ContactListener {

    private final GameWorld gameworld;
    private final GraphicsContext gc;
    private final Difficulty difficulty;
    private final GameView gv;
    private boolean puckReset = false;
    final int scale = 10;

    int counter = 0;
    int round = 1; //Keep track of how many rounds are played

    /**
     * Creates a new instance of a GameManager
     *
     * @param gc The GraphicsContext to draw on.
     * @param players The players that will be playing this game.
     * @param difficulty The difficulty of the AI.
     */
    public GameManager(GraphicsContext gc, ObservableList<IPlayer> players, Difficulty difficulty, GameView gv) {
        this.gc = gc;
        this.gv = gv;
        gameworld = new GameWorld(players);
        this.difficulty = difficulty;
        addContactListener(this);
    }

    /**
     * Draws the sides and puck on to the field.
     */
    public boolean draw() {
        if (round < 11) {
            try {
                Platform.runLater(() -> {
                    gc.setFill(Color.WHITESMOKE);
                    gc.fillRect(0.0, 0.0, 500, 500);
                    AI_CalculateMovement();
                    //Draw field
                    drawField(Color.RED, Color.GREEN, Color.BLUE);

                    drawPuckandPod();

                    if (puckReset) {
                        gameworld.resetPuck();
                        puckReset = false;
                    }
                });

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return true;
        } else {
            //Display ended message
            gv.SetEndLabel();
	    SoundManager.play(8);
            //Stop gameworld
            gameworld.getPhysWorld().clearForces();
            return false;
        }
    }

    /**
     * Draws the field. Refactored from Draw().
     *
     * @param colorBottom Color of the bottom side
     * @param colorRight Color of the right side
     * @param colorLeft Color of the left side
     */
    public void drawField(Color colorBottom, Color colorRight, Color colorLeft) {
        // fieldcorners
        Vec2 field_bottomleft = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.A));
        Vec2 field_top = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.I));
        Vec2 field_bottomright = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.D));

        // goalcorners / sides
        Vec2 goal_bottomleft = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.B));
        Vec2 goal_bottomright = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.C));
        Vec2 goal_lefttop = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.E));
        Vec2 goal_leftbottom = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.F));
        Vec2 goal_rightbottom = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.G));
        Vec2 goal_righttop = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.H));

        //Draw bottom side
        gc.setStroke(colorBottom);
        gc.setFill(colorBottom);
        {
            // Bottom left corner to goal
            gc.strokeLine(
                    field_bottomleft.x,
                    field_bottomleft.y,
                    goal_bottomleft.x,
                    goal_bottomleft.y);

            // Drawing goal down
            gc.strokeLine(
                    goal_bottomleft.x,
                    goal_bottomleft.y,
                    goal_bottomleft.x,
                    goal_bottomleft.y - 20 //goal depth
            );

            //  Drawing goal
            gc.strokeLine(
                    goal_bottomright.x,
                    goal_bottomright.y,
                    goal_bottomright.x,
                    goal_bottomright.y - 20 //goal depth
            );

            //  Drawing goal down
            gc.strokeLine(
                    goal_bottomright.x,
                    goal_bottomright.y - 20, //goal depth
                    goal_bottomleft.x,
                    goal_bottomleft.y - 20 //goal depth
            );

            // shadow
            gc.setStroke(Color.GREY);
            gc.strokeLine(
                    goal_bottomleft.x,
                    goal_bottomleft.y,
                    goal_bottomright.x,
                    goal_bottomright.y);
            gc.setStroke(colorBottom);

            // Bottom right to goal
            gc.strokeLine(
                    field_bottomright.x,
                    field_bottomright.y,
                    goal_bottomright.x,
                    goal_bottomright.y);
        }

        //Drawing left side
        gc.setStroke(colorLeft);
        gc.setFill(colorLeft);
        {
            //Draw from top of the field to goal
            gc.strokeLine(
                    field_top.x,
                    field_top.y,
                    goal_lefttop.x,
                    goal_lefttop.y);

            //Draw goal in
            gc.strokeLine(
                    goal_lefttop.x,
                    goal_lefttop.y,
                    goal_lefttop.x - 18, //goal depth
                    goal_lefttop.y + 12 //goal depth
            );

            //Draw goal in
            gc.strokeLine(
                    goal_leftbottom.x,
                    goal_leftbottom.y,
                    goal_leftbottom.x - 18, //goal depth
                    goal_leftbottom.y + 12 //goal depth
            );

            //Draw goal
            gc.strokeLine(
                    goal_lefttop.x - 18, //goal depth
                    goal_lefttop.y + 12, //goal depth
                    goal_leftbottom.x - 18, //goal depth
                    goal_leftbottom.y + 12 //goal depth
            );

            // shadow
            gc.setStroke(Color.GREY);
            gc.strokeLine(
                    goal_lefttop.x,
                    goal_lefttop.y,
                    goal_leftbottom.x,
                    goal_leftbottom.y);
            gc.setStroke(colorLeft);

            //Draw side from bottom left to goal
            gc.strokeLine(
                    field_bottomleft.x,
                    field_bottomleft.y,
                    goal_leftbottom.x,
                    goal_leftbottom.y);
        }

        //Draw right side
        gc.setStroke(colorRight);
        gc.setFill(colorRight);
        {
            //From bottom right corner to goal
            gc.strokeLine(
                    field_bottomright.x,
                    field_bottomright.y,
                    goal_rightbottom.x,
                    goal_rightbottom.y);

            // Goal in
            gc.strokeLine(
                    goal_righttop.x,
                    goal_righttop.y,
                    goal_righttop.x + 18, //goal depth
                    goal_righttop.y + 12 //goal depth
            );

            // Goal in
            gc.strokeLine(
                    goal_rightbottom.x,
                    goal_rightbottom.y,
                    goal_rightbottom.x + 18, //goal depth
                    goal_rightbottom.y + 12 //goal depth
            );

            // Draw goal
            gc.strokeLine(
                    goal_righttop.x + 18,
                    goal_righttop.y + 12,
                    goal_rightbottom.x + 18,
                    goal_rightbottom.y + 12
            );

            // Draw goal line
            gc.setStroke(Color.GRAY);
            gc.strokeLine(
                    goal_rightbottom.x,
                    goal_rightbottom.y,
                    goal_righttop.x,
                    goal_righttop.y);
            gc.setStroke(colorRight);

            // Draw side from top to goal
            gc.strokeLine(
                    field_top.x,
                    field_top.y,
                    goal_righttop.x,
                    goal_righttop.y);
        }
    }

    /**
     * Scales a vector
     *
     * @param vector a Vec2 object to convert.
     */
    private Vec2 Convert(Vec2 vector) {
        vector.x = vector.x * scale;
        vector.y = vector.y * scale;

        return vector;
    }

    /**
     * Draws the puck and the 3 pods
     */
    public void drawPuckandPod() {
        Body bodylist = gameworld.getPhysWorld().getBodyList();
        //While there are physics items, check if circle
        while (bodylist != null) {
            //Get position and type
            Vec2 pos = bodylist.getPosition();
            ShapeType type = bodylist.getFixtureList().getShape().getType();
            //Draw circles
            if (type == ShapeType.CIRCLE && bodylist.getUserData() instanceof Pod) {
                if (counter == 0) {
                    gc.setStroke(Color.GREEN);
                    gc.setFill(Color.GREEN);
                    counter++;
                } else if (counter == 1) {
                    gc.setStroke(Color.BLUE);
                    gc.setFill(Color.BLUE);
                    counter++;
                } else if (counter == 2) {
                    gc.setStroke(Color.RED);
                    gc.setFill(Color.RED);
                    counter = 0;
                }
            } else if (type == ShapeType.CIRCLE && bodylist.getUserData() instanceof Puck) {
                gc.setStroke(Color.BLACK);
                gc.setFill(Color.BLACK);
            }
            if (type == ShapeType.CIRCLE) {
                CircleShape cs = (CircleShape) bodylist.getFixtureList().getShape();
                float x = pos.x - cs.getRadius();
                float y = pos.y - cs.getRadius();
                Vec2 drawingPos = Convert(new Vec2(x, y));
                gc.fillOval(drawingPos.x, drawingPos.y, cs.getRadius() * scale * 2, cs.getRadius() * scale * 2);
            }
            bodylist = bodylist.getNext();
        }
    }

    /**
     * Part of slowly phasing out the AI from the Controller to the AI classes.
     * Method content should be moved.
     */
    private void AI_CalculateMovement() {
        // temp | note-to-self: avoid hardcoding     
        float p1Y = gameworld.getPod(1).getPosition().y; // AI #1     
        float p2Y = gameworld.getPod(2).getPosition().y; // AI #2
        float puckY = gameworld.getPuck().getPosition().y;

        /* Measure distance - whether y is lower or higher, the distance will 
         * always be a positive number.
         */
        float distanceBlue = puckY - p1Y;
        if (distanceBlue < 0) {
            distanceBlue -= distanceBlue + distanceBlue;
        }

        float distanceGreen = puckY - p2Y;
        if (distanceGreen < 0) {
            distanceGreen -= distanceGreen + distanceGreen;
        }

        // Create deadzone to prevent flickering.
        //When in doubt, set to 25. jus werks.
        float personalspaceBlue = 0;
        float personalspaceGreen = 0;
        if (difficulty == Difficulty.EASY) {
            personalspaceBlue = 5;
            personalspaceGreen = 6;
        } else if (difficulty == Difficulty.NORMAL) {
            personalspaceBlue = 3;
            personalspaceGreen = 4;
        } else if (difficulty == Difficulty.HARD) {
            personalspaceBlue = 1;
            personalspaceGreen = 2;
        }

        // Where is the puck?
        if (puckY < p1Y) {
            // Does the AI respect the puck's personal space?                
            if (distanceBlue > personalspaceBlue) {
                AI_moveUp("BLUE");
            }

        }
        if (puckY < p2Y) {
            if (distanceGreen > personalspaceGreen) {
                AI_moveUp("GREEN");
            }
        }

        // Where is the puck?
        if (puckY > p1Y) {
            // Does the AI respect the puck's personal space?                
            if (distanceBlue > personalspaceBlue) {
                AI_moveDown("BLUE");
            }

        }
        if (puckY > p2Y) {
            if (distanceGreen > personalspaceGreen) {
                AI_moveDown("GREEN");
            }
        }
    }

    /**
     * Moves the AI up from player viewpoint.
     *
     * @param color The color of the pod to move. "BLUE" or "GREEN" allowed
     */
    private void AI_moveUp(String color) {
        if (color.equals("BLUE")) {
            if (gameworld.getPod(1).getPosition().y > MathUtillities.getCoordinates(MathUtillities.Corner.F).y + MathUtillities.getPodSize() / 2) {
                gameworld.getPod(1).moveLeft(1);
            }
        }
        if (color.equals("GREEN")) {
            if (gameworld.getPod(2).getPosition().y > MathUtillities.getCoordinates(MathUtillities.Corner.G).y + MathUtillities.getPodSize() / 2) {
                gameworld.getPod(2).moveRight(2);
            }
        }
    }

    /**
     * Moves the AI down from player viewpoint.
     *
     * @param color The color of the pod to move. "BLUE" or "GREEN" allowed
     */
    private void AI_moveDown(String color) {
        if (color.equals("BLUE")) {
            if (gameworld.getPod(1).getPosition().y < MathUtillities.getCoordinates(MathUtillities.Corner.E).y - MathUtillities.getPodSize() / 2) {
                gameworld.getPod(1).moveRight(1);
            }
        }
        if (color.equals("GREEN")) {
            if (gameworld.getPod(2).getPosition().y < MathUtillities.getCoordinates(MathUtillities.Corner.H).y - MathUtillities.getPodSize() / 2) {
                gameworld.getPod(2).moveLeft(2);
            }
        }
    }

    /**
     * *************TODO***************
     */
    //This one may need some refactoring??
    /**
     * Is the player moving right or left?
     *
     * @param playerMoveRight
     * @param playerMoveLeft
     */
    public void player_Move(boolean playerMoveRight, boolean playerMoveLeft) {
        if (playerMoveRight) {
            if (gameworld.getPod(0).getPosition().x < MathUtillities.getCoordinates(MathUtillities.Corner.C).x - MathUtillities.getPodSize() / 2) {
                gameworld.getPod(0).moveRight(0);
            }
        }
        if (playerMoveLeft) {
            if (gameworld.getPod(0).getPosition().x > MathUtillities.getCoordinates(MathUtillities.Corner.B).x + MathUtillities.getPodSize() / 2) {
                gameworld.getPod(0).moveLeft(0);
            }
        }
    }

    /**
     * Method to start the physics simulation
     */
    public void start() {
        try {
            Timer physTimer = new Timer("Simulate Physics", true);
            physTimer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    gameworld.getPhysWorld().step(1 / 60f, 10, 5);
                    if (round > 10) {
                        this.cancel();
                    }
                }
            }, 0, (long) (1 / 0.06));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Adds a listener for collisions that happen in the game
     *
     * @param cl A class implementing ContactListener
     */
    public void addContactListener(ContactListener cl) {
        gameworld.getPhysWorld().setContactListener(cl);
    }

    /**
     * Method is called when a collision occurs
     *
     * @param cntct
     */
    @Override
    public void beginContact(Contact cntct) {
        Body bodyA = cntct.getFixtureA().getBody();
        Body bodyB = cntct.getFixtureB().getBody();
        if (bodyA.getUserData() instanceof Puck && bodyB.getUserData() instanceof Goal) {
	    
	    SoundManager.play(1); // Hits pod
	    
            Goal g = (Goal) bodyB.getUserData();
            Puck puck = (Puck) bodyA.getUserData();
            if (puck.getTouched(0) != null) {
                if (puck.getTouched(0).getPlayer() == g.getPlayer()) {
                    if (puck.getTouched(1) != null && puck.getTouched(1).getPlayer() != g.getPlayer()) {
                        puck.getTouched(1).getPlayer().setRanking(true);
                        g.getPlayer().setRanking(false);
			
			SoundManager.play(3); // Scores
			
                        System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                        System.out.println("plus: " + puck.getTouched(1).getPlayer().getName() + " " + puck.getTouched(1).getPlayer().getRanking());
                    } else {
                        g.getPlayer().setRanking(false);
                        System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
			
			SoundManager.play(5); // Own goal
			
                    }
                } else {
                    puck.getTouched(0).getPlayer().setRanking(true);
                    g.getPlayer().setRanking(false);
                    System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                    System.out.println("plus: " + puck.getTouched(0).getPlayer().getName() + " " + puck.getTouched(0).getPlayer().getRanking());
		    
		    SoundManager.play(4); // Scores
		    
                }
		SoundManager.play(0); // Scores
                //Set next round
                round++;
            }
            //Reset puck
            puckReset = true;
        } else if (bodyB.getUserData() instanceof Puck && bodyA.getUserData() instanceof Goal) {
	    
	    SoundManager.play(1); // Hits pod
	    
            Goal g = (Goal) bodyA.getUserData();
            Puck puck = (Puck) bodyB.getUserData();
            if (puck.getTouched(0) != null) {
                if (puck.getTouched(0).getPlayer() == g.getPlayer()) {
                    if (puck.getTouched(1) != null && puck.getTouched(1).getPlayer() != g.getPlayer()) {
                        puck.getTouched(1).getPlayer().setRanking(true);
                        g.getPlayer().setRanking(false);
                        System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                        System.out.println("plus: " + puck.getTouched(1).getPlayer().getName() + " " + puck.getTouched(1).getPlayer().getRanking());
                    } else {
                        g.getPlayer().setRanking(false);
                        System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
			
			SoundManager.play(3); // Scores
			
                    }
                } else {
                    puck.getTouched(0).getPlayer().setRanking(true);
                    g.getPlayer().setRanking(false);
                    System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                    System.out.println("plus: " + puck.getTouched(0).getPlayer().getName() + " " + puck.getTouched(0).getPlayer().getRanking());
		    
		    SoundManager.play(4); // Scores
		    
                }
                //Set next round
                round++;
		SoundManager.play(0); // Scores
            }
            //Reset puck
            puckReset = true;
        }
        if (bodyA.getUserData() instanceof Puck && bodyB.getUserData() instanceof Pod) {	    	    
            Puck puck = (Puck) bodyA.getUserData();
            Pod pod = (Pod) bodyB.getUserData();
            puck.addTouched(pod);
	    
	    SoundManager.play(0); // Scores
	    
        } else if (bodyB.getUserData() instanceof Puck && bodyA.getUserData() instanceof Pod) {	    
	    
            Puck puck = (Puck) bodyB.getUserData();
            Pod pod = (Pod) bodyA.getUserData();
            puck.addTouched(pod);
	    
	    SoundManager.play(5); // Scores
        }
    }

    /**
     * Method is called when collision ends
     *
     * @param cntct
     */
    @Override
    public void endContact(Contact cntct) {
    }

    /**
     * Method is called when collision is about to occur
     *
     * @param cntct
     * @param mnfld
     */
    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
    }

    /**
     * Method is called after collision has ended.
     *
     * @param cntct
     * @param ci
     */
    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
}
