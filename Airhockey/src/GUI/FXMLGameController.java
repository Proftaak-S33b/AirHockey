package GUI;

//<editor-fold defaultstate="collapsed" desc="imports">
import game.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
//</editor-fold>

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class FXMLGameController implements Initializable, EventHandler<KeyEvent>, ContactListener, Observer {

    @FXML
    private Button buttonChat;

    @FXML
    private TextField textChat;

    @FXML
    private ListView listChat;

    @FXML
    private TableView tableScore;

    @FXML
    private TableColumn columnPlayer;

    @FXML
    private TableColumn columnScore;

    @FXML
    private Canvas gameCanvas;

    private PhysicsMediator pm;

    GraphicsContext gc;

    /**
     * AI Mediator
     */
    private AI_Mediator mediator;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GameWorld world;
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Human("Henk", "fiets"));
        players.add(new AI("Player 2"));
        players.add(new AI("Player 3"));
        world = new GameWorld(players);
        pm = new PhysicsMediator(world, this);
        pm.addContactListener(this);

        gc = gameCanvas.getGraphicsContext2D();

        /**
         * Initializes the mediator with the current controller, players, and
         * puck.
         */
        mediator = new AI_Mediator(this, players, world.getPuck());

        Draw();

        new AnimationTimer() {

            @Override
            public void handle(long now) {
                Draw();
            }
        }.start();

        try {
            Timer physTimer = new Timer("Simulate Physics", true);
            physTimer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    pm.step(1 / 60f, 10, 5);
                }
            }, 0, (long) (1 / 0.06));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void SetTekst(ActionEvent event) {
        listChat.getItems().add(textChat.getText());
        textChat.clear();
    }

    /**
     * Draws the sides and puck on the field.
     */
    public void Draw() {
        try {

            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(0.0, 0.0, 550, 550);

            //Blue side
            drawSide(Color.BLUE, "Player 2", 0, 1, -18, -12);

            //Green side
            drawSide(Color.GREEN, "Player 3", 1, 2, 18, -12);

            //Red side
            drawSide(Color.RED, "Henk", 2, 0, 0, 20);

            drawPuck();

            drawPod();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Draws a given side. Refactored from Draw().
     *
     * @param color a Color enum indicating the color of the lines.
     * @param playername a String with the name of the player.
     * @param a an int representing the first intersecting corner of the field.
     * @param b an int representing the second intersecting corner of the field.
     * @param sizeX a double representing the width of the goal.
     * @param sizeY a double representing the length of the goal.
     */
    public void drawSide(Color color, String playername, int a, int b, double sizeX, double sizeY) {
        gc.setStroke(color);

        ArrayList<Vec2> corners = pm.getFieldCorners();

        float aX = corners.get(a).x;
        float aY = corners.get(a).y;
        float bX = corners.get(b).x;
        float bY = corners.get(b).y;

        gc.strokeLine(aX, aY, bX, bY);
        ArrayList<Vec2> goalCoordinates = pm.getGoalCorners(aX, aY, bX, bY, sizeX, sizeY);

        // strokePolygon is done undynamically so a loop suffices.
        double[] goalXcoords = new double[4];
        double[] goalYcoords = new double[4];

        for (int i = 0; i < 4; i++) {
            goalXcoords[i] = goalCoordinates.get(i).x;
            goalYcoords[i] = goalCoordinates.get(i).y;
        }

        gc.strokePolygon(goalXcoords, goalYcoords, 4);
        gc.setFill(color);

        try {
            gc.fillOval(
                    pm.getPodPosition(pm.getPodIndex(playername)).x,
                    pm.getPodPosition(pm.getPodIndex(playername)).y,
                    pm.getPodSize(),
                    pm.getPodSize()
            );
        } catch (NoSuchFieldException ex) {
            System.out.println("Playername not found" + ex.getMessage());
        }
    }

    /**
     * Draws the puck. Refactored from Draw().
     */
    public void drawPuck() {
        gc.setFill(Color.BLACK);
        gc.fillOval(pm.getPuckPosition().x, pm.getPuckPosition().y, pm.getPuckSize(), pm.getPuckSize());
    }

    /**
     * Draws an individual pod.
     */
    public void drawPod() {
        // temp | note-to-self: avoid hardcoding     
        float p1Y = pm.getPodPosition(1).y; // AI #1     
        float p2Y = pm.getPodPosition(2).y; // AI #2
        float puckY = pm.getPuckPosition().y;

        ArrayList<Vec2> corners = pm.getFieldCorners();

        float aX = corners.get(0).x;
        float aY = corners.get(0).y;
        float bX = corners.get(1).x;
        float bY = corners.get(1).y;

        ArrayList<Vec2> goalCoordinates = pm.getGoalCorners(aX, aY, bX, bY, -18, -12);

        
        // AI    ///////////////////////////////////////////////////////////////
        
        // Measure distance - whether y is lower or higher, the distance will 
        // always be a positive number.
        float distance = puckY - p2Y;
        if (distance < 0) {
            distance -= distance + distance;
        }
        
        // Create deadzone to prevent flickering.
        float personalspace = (float)pm.getPodSize() / 2;
        
        // Where is the puck?
        if (puckY < p1Y & puckY < p2Y) {
            if (distance > 25) {
                AI_moveUp(goalCoordinates);
            }
            // Does the AI respect the puck's personal space?                
        }
        if (puckY > p1Y & puckY > p2Y) {
            if (distance > 25) {
                AI_moveDown(goalCoordinates); 
            }                
        }
        
        System.out.println("Distance: " + distance + ", puck.y: " + puckY + ", pod.y: " + p2Y);
    }
    
    /**
     * Moves the AI up from player viewpoints.
     * @param goalCoordinates 
     */
    private void AI_moveUp(ArrayList<Vec2> goalCoordinates){
            if (pm.getPodPosition(1).y > goalCoordinates.get(2).y + pm.getPodSize() / 2) {
                pm.movePodLeft(1);
                pm.movePodRight(2);
            }
    }
    
    /**
     * Moves the AI down from player viewpoint.
     * @param goalCoordinates 
     */
    private void AI_moveDown(ArrayList<Vec2> goalCoordinates){
            if (pm.getPodPosition(1).y < goalCoordinates.get(0).y - pm.getPodSize()) {
                pm.movePodLeft(2);
                pm.movePodRight(1);
            }
    }

    @Override
    public void handle(KeyEvent e) {
        ArrayList<Vec2> corners = pm.getFieldCorners();

        float aX = corners.get(2).x;
        float aY = corners.get(2).y;
        float bX = corners.get(0).x;
        float bY = corners.get(0).y;

        ArrayList<Vec2> goalCoordinates = pm.getGoalCorners(aX, aY, bX, bY, 0, 20);

        Vec2 v = pm.getPodPosition(0);

        switch (e.getCharacter()) {
            case "a":
                if (pm.getPodPosition(0).x > goalCoordinates.get(2).x) {
                    pm.movePodLeft(0);
                }
                break;
            case "d":
                if (pm.getPodPosition(0).x < goalCoordinates.get(0).x - 5 - pm.getPodSize()) {
                    pm.movePodRight(0);
                }
                break;
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        if (bodyA.getUserData() instanceof Puck) {
            Player p = pm.hitGoal(bodyA.getPosition());
            if (p != null) {
                //pm.score(null);
                System.out.println(p.getName());
            }
        } else if (bodyB.getUserData() instanceof Puck) {
            Player p = pm.hitGoal(bodyB.getPosition());
            if (p != null) {
                //pm.score(null);
                System.out.println(p.getName());
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    /**
     * Updates the position of the AI's pods. TODO: Heavy refactoring; only
     * redraw what needs to be redrawn(eg. puck and pods).
     *
     * @param arg
     */
    public void updatePodPositions(Object arg) {
    }

    /**
     * TODO: replace updatePodPositions.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
    }

}
