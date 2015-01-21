package controllers;

//<editor-fold defaultstate="collapsed" desc="imports">
import GUI.GameView;
import game.AI.Difficulty;
import game.GameWorld;
import game.Goal;
import game.MathUtillities;
import game.Pod;
import game.Puck;
import game.Wall;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import networking.IPlayer;
import networking.sockets.Client;
import networking.standalone.Lobby;
import networking.standalone.Server;
import networking.standalone.rmiDefaults;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.collision.shapes.CircleShape;
//</editor-fold>

/**
 * Controller for managing game logic
 *
 * @author Maikel
 */
public class GameManager implements ContactListener, ChangeListener<String> {

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Platform.runLater(() -> {
            gv.listChat.getItems().add(newValue);
        });
    }

    /**
     * Enum that shows the type of game
     */
    public enum GameType {

        SINGLEPLAYER,
        MULTIPLAYER_RED,
        MULTIPLAYER_BLUE,
        MULTIPLAYER_GREEN,
        SPECTATING
    }

    private final GameType gameType;
    private final GameWorld gameworld;
    private final GraphicsContext gc;
    private final Difficulty difficulty;
    private final GameView gv;
    private final Lobby lobby;
//    private IRemoteGame remoteGame;
    private Server server;
    private Client client;
    private boolean puckReset = false;
    private Timer physTimer = null;

    /**
     * Indicates whether the field- and goalcorners need to be (re-)calculated.
     */
    private boolean fieldReset = true;

    final int scale = 10;

    //<editor-fold defaultstate="collapsed" desc="private field data">
    // fieldcorners
    private Vec2 field_bottomleft;
    private Vec2 field_top;
    private Vec2 field_bottomright;

    // goalcorners / sides
    private Vec2 goal_bottomleft;
    private Vec2 goal_bottomright;
    private Vec2 goal_lefttop;
    private Vec2 goal_leftbottom;
    private Vec2 goal_rightbottom;
    private Vec2 goal_righttop;

    // Offsets for goal depths.
    private int yOffset_Bottom = 20;
    private int xOffset_LeftRight = 18;
    private int yOffset_LeftRight = 12;

    // Goalcorners, corrected with related offsets.
    private Vec2 goal_bottomleft_offset;
    private Vec2 goal_bottomright_offset;
    private Vec2 goal_righttop_offset;
    private Vec2 goal_rightbottom_offset;
    private Vec2 goal_lefttop_offset;
    private Vec2 goal_leftbottom_offset;

    //</editor-fold>
    int counter = 0;
    int round = 1; //Keep track of how many rounds are played

    /**
     * Creates a new instance of a GameManager
     *
     * @param gc The GraphicsContext to draw on.
     * @param players The players that will be playing this game.
     * @param difficulty The difficulty of the AI.
     * @param gameType The type of game for the client this gamemanager manages
     * the game of
     * @param gv The GUI Controller //should not be here!!!
     * @param lobby
     * @param clientData
     */
    public GameManager(GraphicsContext gc, ObservableList<IPlayer> players, Difficulty difficulty, GameType gameType, GameView gv, Lobby lobby) {
        this.gc = gc;
        this.gv = gv;
        this.lobby = lobby;
        gameworld = new GameWorld(players);
        this.difficulty = difficulty;
        this.gameType = gameType;
        gameworld.getPhysWorld().setContactListener(this);
        if (gameType == GameType.MULTIPLAYER_RED) {
            try { 
                client = new Client(rmiDefaults.DEFAULT_SERVER_IP(), 4444, this);
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                client = new Client(rmiDefaults.DEFAULT_SERVER_IP(), 4444, this);
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   
    /**
     * Sends a text message to the server which distributes it over all clients
     * @param message the text message to be sent
     * @param sender the IPlayer who sent this message
     */
    public void sendMessage(String message, IPlayer sender){
        client.sendMessage(message, sender);
    }
//
//    private IRemoteGame startServer() {
//        try {
//            //Send this to central server
//            //server.getServerIP();
//            IRemoteGame game = lobby.getRemoteGame();
//            Vec2 redPodPos = gameworld.getPod(0).getPosition();
//            Vec2 puckPos = gameworld.getPuck().getPosition();
//            Vec2 puckVel = gameworld.getPuck().getBody().getLinearVelocity();
//            game.setHostData(
//                    new Coordinate(redPodPos.x, redPodPos.y),
//                    new Coordinate(puckPos.x, puckPos.y),
//                    new Coordinate(puckVel.x, puckVel.y),
//                    gameworld.getPlayers().get(0).getRanking(),
//                    gameworld.getPlayers().get(1).getRanking(),
//                    gameworld.getPlayers().get(2).getRanking(),
//                    round);
//            return game;
//        } catch (RemoteException ex) {
//            System.out.println("RemoteException: " + ex.getMessage());
//        }
//        return null;
//    }
//
//    private IRemoteGame connectToServer(InetAddress address, int port) {
//        Client rmi = new Client(address.getHostAddress(), port);
//        ILobby l = (ILobby) rmi.lookup("hockeygame");
//        try {
//            return l.getRemoteGame();
//        } catch (RemoteException ex) {
//            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    /**
     * Draws the sides and puck on to the field.
     *
     * @return
     */
    public boolean draw() {
        if (round < 11) {
            try {
                Platform.runLater(() -> {
                    gc.setFill(Color.WHITESMOKE);
                    gc.fillRect(0.0, 0.0, 500, 500);
                    if (gameType == GameType.SINGLEPLAYER) {
                        AI_CalculateMovement();
                    }
                    //Draw field
                    drawField(Color.RED, Color.GREEN, Color.BLUE);

                    drawPuckandPod();
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

        // If field values arent yet calculated, or need to be reset: recalculate.
        if (fieldReset) {
            fieldInit();
        }

        //Draw bottom side
        drawSide(
                field_bottomleft,
                goal_bottomleft, goal_bottomleft_offset,
                goal_bottomright, goal_bottomright_offset,
                field_bottomright,
                colorBottom
        );

        //Drawing left side
        drawSide(
                field_top,
                goal_lefttop, goal_lefttop_offset,
                goal_leftbottom, goal_leftbottom_offset,
                field_bottomleft,
                colorLeft
        );

        //Draw right side
        drawSide(
                field_bottomright,
                goal_rightbottom, goal_rightbottom_offset,
                goal_righttop, goal_righttop_offset,
                field_top,
                colorRight
        );
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
     * Method content has been moved to AI.java.
     */
    @Deprecated
    private void AI_CalculateMovement() {
        // temp | note-to-self: avoid hardcoding     
        float blueX = gameworld.getPod(1).getPosition().x; // AI #1     
        float blueY = gameworld.getPod(1).getPosition().y; // 
        float greenX = gameworld.getPod(2).getPosition().x; // AI #2
        float greenY = gameworld.getPod(2).getPosition().y; // 
        float puckY = gameworld.getPuck().getPosition().y;
        float puckX = gameworld.getPuck().getPosition().x;

        // Horizontal distance; line of sight of the AI.
        float sight = 0; // This is pure trial-and-error. No set rules.

        // Measure vertical distance - whether y is below or above zero, 
        // the distance will always be a positive number.
        float distanceBlue = Math.abs(puckY - blueY);
        float distanceGreen = Math.abs(puckY - greenY);

        // Create deadzone to prevent AI from having MLG-tier accuracy.
        float personalspaceBlue = 0;
        float personalspaceGreen = 0;
        if (difficulty == Difficulty.EASY) {
            personalspaceBlue = 5;
            personalspaceGreen = 6;
            sight = 11;
        } else if (difficulty == Difficulty.NORMAL) {
            personalspaceBlue = 3;
            personalspaceGreen = 4;
            sight = 15;
        } else if (difficulty == Difficulty.HARD) {
            personalspaceBlue = 1;
            personalspaceGreen = 2;
            sight = 99;
        }

        boolean withinrange = puckY < blueY;
        boolean inlineofsight = Math.abs(puckX - blueX) < sight;
        // Where is the puck? Can the AI *see* the puck?
        if (withinrange && inlineofsight) {
            // Does the AI respect the puck's personal space?                
            if (distanceBlue > personalspaceBlue) {
                AI_moveUp("BLUE");
            }
        }

        withinrange = puckY > blueY;
        if (withinrange && inlineofsight) {
            // Does the AI respect the puck's personal space?                
            if (distanceBlue > personalspaceBlue) {
                AI_moveDown("BLUE");
            }
        }

        withinrange = puckY < greenY;
        inlineofsight = Math.abs(puckX - greenX) < sight;
        if (withinrange && inlineofsight) {
            if (distanceGreen > personalspaceGreen) {
                AI_moveUp("GREEN");
            }
        }

        withinrange = puckY > greenY;
        if (withinrange && inlineofsight) {
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
     * @param gametype
     */
    public void player_Move(boolean playerMoveRight, boolean playerMoveLeft, GameType gametype) {
        if (gametype == GameType.MULTIPLAYER_RED || gametype == GameType.SINGLEPLAYER) {
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
        if (gametype == GameType.MULTIPLAYER_BLUE) {
            if (playerMoveRight) {
                if (gameworld.getPod(1).getPosition().x < MathUtillities.getCoordinates(MathUtillities.Corner.C).x - MathUtillities.getPodSize() / 2) {
                    gameworld.getPod(1).moveRight(1);
                }
            }
            if (playerMoveLeft) {
                if (gameworld.getPod(1).getPosition().x > MathUtillities.getCoordinates(MathUtillities.Corner.B).x + MathUtillities.getPodSize() / 2) {
                    gameworld.getPod(1).moveLeft(1);
                }
            }
        }
        if (gametype == GameType.MULTIPLAYER_GREEN) {
            if (playerMoveRight) {
                if (gameworld.getPod(2).getPosition().x < MathUtillities.getCoordinates(MathUtillities.Corner.C).x - MathUtillities.getPodSize() / 2) {
                    gameworld.getPod(2).moveRight(2);
                }
            }
            if (playerMoveLeft) {
                if (gameworld.getPod(2).getPosition().x > MathUtillities.getCoordinates(MathUtillities.Corner.B).x + MathUtillities.getPodSize() / 2) {
                    gameworld.getPod(2).moveLeft(2);
                }
            }
        }
    }

    /**
     * Method to start the physics simulation
     */
    public void start() {
        try {
            physTimer = new Timer("Simulate Physics", true);
            physTimer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    gameworld.getPhysWorld().step(1 / 60f, 10, 5);
                    if (puckReset) {
                        gameworld.resetPuck();
                        puckReset = false;
                    }
                    if (round > 10) {
                        this.cancel();
                    }
                    if (gameType == GameType.MULTIPLAYER_BLUE) {
                        Vec2 pos = gameworld.getPod(1).getPosition();
//                        try {
//                            remoteGame.setBluePodPos(new Coordinate(pos.x, pos.y));
//                            GameData data = remoteGame.getGameData();
//                            setLocalData(data);
//                        } catch (RemoteException ex) {
//                            System.out.println("RemoteException: " + ex.getMessage());
//                        }
                    } else if (gameType == GameType.MULTIPLAYER_GREEN) {
                        Vec2 pos = gameworld.getPod(2).getPosition();
//                        try {
//                            remoteGame.setGreenPodPos(new Coordinate(pos.x, pos.y));
//                            GameData data = remoteGame.getGameData();
//                            setLocalData(data);
//                        } catch (RemoteException ex) {
//                            System.out.println("RemoteException: " + ex.getMessage());
//                        }
                    } else if (gameType == GameType.MULTIPLAYER_RED) {
//                        try {
                        Vec2 redPodPos = gameworld.getPod(0).getPosition();
                        Vec2 puckPos = gameworld.getPuck().getPosition();
                        Vec2 puckVel = gameworld.getPuck().getBody().getLinearVelocity();
//                            remoteGame.setHostData(
//                                    new Coordinate(redPodPos.x, redPodPos.y),
//                                    new Coordinate(puckPos.x, puckPos.y),
//                                    new Coordinate(puckVel.x, puckVel.y),
//                                    gameworld.getPlayers().get(0).getRanking(),
//                                    gameworld.getPlayers().get(1).getRanking(),
//                                    gameworld.getPlayers().get(2).getRanking(),
//                                    round);
//                            GameData data = remoteGame.getGameData();
//                            setLocalData(data);
//                        } catch (RemoteException ex) {
//                            System.out.println("RemoteException: " + ex.getMessage());
//                        }
                    }
                }

                
            }, 0, (long) (1 / 0.06));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Adding the sounds was kind of confusing, if someone could clean it up a bit that'd be great.
    // I'd do it but I'm working on RMI atm. :(
    /**
     * Method is called when a collision occurs
     *
     * @param cntct
     */
    @Override
    public void beginContact(Contact cntct) {
        //Only if this class should be in control of game mechanics
        if (gameType == GameType.SINGLEPLAYER || gameType == GameType.MULTIPLAYER_RED) {
            Body bodyA = cntct.getFixtureA().getBody();
            Body bodyB = cntct.getFixtureB().getBody();

            checkCollisionWithWall(bodyA, bodyB);

            if (bodyA.getUserData() instanceof Puck && bodyB.getUserData() instanceof Goal) {

                // Hits goal
                SoundManager.play(SoundManager.SoundEffects.intervention_420);

                Goal g = (Goal) bodyB.getUserData();
                Puck puck = (Puck) bodyA.getUserData();

                if (puck.getTouched(0) != null) {

                    if (puck.getTouched(0).getPlayer() == g.getPlayer()) {

                        if (puck.getTouched(1) != null && puck.getTouched(1).getPlayer() != g.getPlayer()) {

                            puck.getTouched(1).getPlayer().changeRanking(true);
                            g.getPlayer().changeRanking(false);

                            // Scores
                            SoundManager.setTrack(SoundManager.SoundEffects.Oh_Baby_A_Triple);

                            System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                            System.out.println("plus: " + puck.getTouched(1).getPlayer().getName() + " " + puck.getTouched(1).getPlayer().getRanking());
                            System.out.println("---------------------------");

                        } else {

                            g.getPlayer().changeRanking(false);

                            // Own goal
                            SoundManager.play(SoundManager.SoundEffects._2SAD4ME);

                            System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                            System.out.println("---------------------------");

                        }
                    } else {

                        puck.getTouched(0).getPlayer().changeRanking(true);
                        g.getPlayer().changeRanking(false);

                        // Scores
                        SoundManager.setTrack(SoundManager.SoundEffects.intervention_420);

                        System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                        System.out.println("plus: " + puck.getTouched(0).getPlayer().getName() + " " + puck.getTouched(0).getPlayer().getRanking());
                        System.out.println("---------------------------");
                    }
                    //Set next round
                    round++;
                }
                //Reset puck
                puckReset = true;
            } else if (bodyB.getUserData() instanceof Puck && bodyA.getUserData() instanceof Goal) {

                // Hits goal
                SoundManager.play(SoundManager.SoundEffects.intervention_420);

                Goal g = (Goal) bodyA.getUserData();
                Puck puck = (Puck) bodyB.getUserData();
                if (puck.getTouched(0) != null) {

                    if (puck.getTouched(0).getPlayer() == g.getPlayer()) {

                        if (puck.getTouched(1) != null && puck.getTouched(1).getPlayer() != g.getPlayer()) {

                            puck.getTouched(1).getPlayer().changeRanking(true);
                            g.getPlayer().changeRanking(false);

                            // Scores
                            SoundManager.play(SoundManager.SoundEffects.Oh_Baby_A_Triple);

                            System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                            System.out.println("plus: " + puck.getTouched(1).getPlayer().getName() + " " + puck.getTouched(1).getPlayer().getRanking());
                            System.out.println("---------------------------");
                        } else {

                            g.getPlayer().changeRanking(false);

                            // Scores
                            SoundManager.play(SoundManager.SoundEffects._2SAD4ME);

                            System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                            System.out.println("---------------------------");
                        }
                    } else {

                        puck.getTouched(0).getPlayer().changeRanking(true);
                        g.getPlayer().changeRanking(false);

                        // Scores
                        SoundManager.play(SoundManager.SoundEffects.DAMN_SON_WHERED_YOU_FIND_THIS);

                        System.out.println("min: " + g.getPlayer().getName() + " " + g.getPlayer().getRanking());
                        System.out.println("plus: " + puck.getTouched(0).getPlayer().getName() + " " + puck.getTouched(0).getPlayer().getRanking());
                        System.out.println("---------------------------");
                    }
                    //Set next round
                    round++;
                }
                //Reset puck
                puckReset = true;
            }
            if (bodyA.getUserData() instanceof Puck && bodyB.getUserData() instanceof Pod) {

                // Hits pod
                SoundManager.play(SoundManager.SoundEffects.intervention_420);

                Puck puck = (Puck) bodyA.getUserData();
                Pod pod = (Pod) bodyB.getUserData();
                puck.addTouched(pod);
            } else if (bodyB.getUserData() instanceof Puck && bodyA.getUserData() instanceof Pod) {

                // Hits pod
                SoundManager.play(SoundManager.SoundEffects.intervention_420);

                Puck puck = (Puck) bodyB.getUserData();
                Pod pod = (Pod) bodyA.getUserData();
                puck.addTouched(pod);
            }
        }
    }

    /**
     * <i>Extracted from beginContact.</i>
     * Checks if the puck collides with the wall, and if so, plays sound.
     *
     * @param bodyA see beginContact().
     * @param bodyB see beginContact().
     */
    private void checkCollisionWithWall(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() instanceof Puck && bodyB.getUserData() instanceof Wall) {
            SoundManager.play(SoundManager.SoundEffects.hitmarker);
        }
        if (bodyB.getUserData() instanceof Puck && bodyA.getUserData() instanceof Wall) {
            SoundManager.play(SoundManager.SoundEffects.hitmarker);
        }
    }

    /**
     * <i>Extracted from drawfield, prevents repeating of slightly different
     * lines.</i>
     * Draws a line between two vectors using strokeLine.
     *
     * @param from a Vec2 starting point for the line.
     * @param to a Vec2 endpoint for the line.
     */
    private void drawLine(Vec2 from, Vec2 to) {
        gc.strokeLine(from.x, from.y, to.x, to.y);
    }

    /**
     * Draws an individual side in one move with the right colors.
     *
     * @param field_a starting corner from field.
     * @param goalpost1 goalpost to draw to from field_a.
     * @param goalpost1_offset offsetted version of previous goalpost.
     * @param goalpost2 goalpost to draw from to field_b.
     * @param goalpost2_offset offsetted version of previous goalpost.
     * @param field_b other corner of the field.
     * @param color color of the side.
     */
    private void drawSide(
            Vec2 field_a,
            Vec2 goalpost1, Vec2 goalpost1_offset,
            Vec2 goalpost2, Vec2 goalpost2_offset,
            Vec2 field_b,
            Color color) {
        gc.setStroke(color);
        gc.setFill(color);

        //from field to goal
        drawLine(field_a, goalpost1);

        //goal posts one side
        drawLine(goalpost1, goalpost1_offset);

        //goalposts other side
        drawLine(goalpost2, goalpost2_offset);

        //net inbetween
        drawLine(goalpost1_offset, goalpost2_offset);

        gc.setStroke(Color.GRAY);
        //shadow inbetween
        drawLine(goalpost1, goalpost2);

        gc.setStroke(color);
        //from goal to field
        drawLine(goalpost2, field_b);
    }

    /**
     * Initializes the field so it doesn't have to recalculate constants every
     * draw.
     */
    private void fieldInit() {
        // fieldcorners
        field_bottomleft = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.A));
        field_top = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.I));
        field_bottomright = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.D));

        // goalcorners / sides
        goal_bottomleft = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.B));
        goal_bottomright = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.C));
        goal_lefttop = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.E));
        goal_leftbottom = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.F));
        goal_rightbottom = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.G));
        goal_righttop = Convert(MathUtillities.getCoordinates(MathUtillities.Corner.H));

        // Offsets for goal depths.
        yOffset_Bottom = 20;
        xOffset_LeftRight = 18;
        yOffset_LeftRight = 12;

        // Goalcorners, corrected with related offsets.
        goal_bottomleft_offset = goal_bottomleft_offset = new Vec2(goal_bottomleft.x, goal_bottomleft.y - yOffset_Bottom);
        goal_bottomright_offset = goal_bottomright_offset = new Vec2(goal_bottomright.x, goal_bottomright.y - yOffset_Bottom);
        goal_righttop_offset = goal_righttop_offset = new Vec2(goal_righttop.x + xOffset_LeftRight, goal_righttop.y + yOffset_LeftRight);
        goal_rightbottom_offset = goal_rightbottom_offset = new Vec2(goal_rightbottom.x + xOffset_LeftRight, goal_rightbottom.y + yOffset_LeftRight);
        goal_lefttop_offset = goal_lefttop_offset = new Vec2(goal_lefttop.x - xOffset_LeftRight, goal_lefttop.y + yOffset_LeftRight);
        goal_leftbottom_offset = goal_leftbottom_offset = new Vec2(goal_leftbottom.x - xOffset_LeftRight, goal_leftbottom.y + yOffset_LeftRight);

        fieldReset = false;
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

    public void destroy() {
        if (physTimer != null) {
            physTimer.cancel();
        }
    }
}
