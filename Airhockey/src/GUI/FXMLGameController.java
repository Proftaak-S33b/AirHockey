package GUI;

//<editor-fold defaultstate="collapsed" desc="imports">
import com.sun.media.jfxmedia.logging.Logger;
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

    private game.GameWorld world;
    GraphicsContext gc;
    ArrayList<Vec2> corners;
    ArrayList<Vec2> goal;
    
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
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Human("Henk", "fiets"));
        players.add(new AI("Player 2"));
        players.add(new AI("Player 3"));
        world = new GameWorld(players);
        world.getPhysWorld().setContactListener(this);
        gc = gameCanvas.getGraphicsContext2D();
        corners = world.getField().getFieldCorners();

        /**
         * Initializes the mediator with the current controller, players, and puck.
         */
        mediator = new AI_Mediator(this, players, world.getPuck());
        
        Draw();        

                
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                Draw();
                //gc.setFill(Color.BLACK);
                //gc.fillOval(world.getPuck().getPosition().x, world.getPuck().getPosition().y, world.getField().getPuckSize(), world.getField().getPuckSize());
                //drawPuck(); can replace this - needs to be tested 
                //Redundant code - Draw() now includes puck.
            }
        }.start();

        try {
            Timer physTimer = new Timer("Simulate Physics", true);
            physTimer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    world.getPhysWorld().step(1 / 60.0f, 8, 3);
                    //Puckspeed gets set everywhere but this one is the only one that counts it seems.
                    world.getPuck().setSpeed(100);  
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
     * @param color a Color enum indicating the color of the lines.
     * @param playername a String with the name of the player.
     * @param a an int representing the first intersecting corner of the field.
     * @param b an int representing the second intersecting corner of the field.
     * @param sizeX a double representing the width of the goal.
     * @param sizeY a double representing the length of the goal.
     */
    public void drawSide(Color color, String playername, int a, int b, double sizeX, double sizeY){       
        gc.setStroke(color);        
        
        float aX = corners.get(a).x;
        float aY = corners.get(a).y;
        float bX = corners.get(b).x;
        float bY = corners.get(b).y;
                        
        gc.strokeLine(aX, aY, bX, bY);
        goal = world.getField().getGoalCorners(aX, aY, bX, bY, sizeX, sizeY);
        
        // strokePolygon is done undynamically so a loop suffices.
        double[] goalXcoords = new double[4];
        double[] goalYcoords = new double[4];
        
        for (int i = 0; i < 4; i++) {
            goalXcoords[i] = goal.get(i).x;
            goalYcoords[i] = goal.get(i).y;
        }
        
        gc.strokePolygon(goalXcoords, goalYcoords, 4);        
        gc.setFill(color);
        gc.fillOval(
                world.getPod(playername).getPosition().x, 
                world.getPod(playername).getPosition().y,
                world.getField().getPodSize(),
                world.getField().getPodSize()
        );
    }

    /**
     * Draws the puck. Refactored from Draw().
     */
    public void drawPuck(){
        gc.setFill(Color.BLACK);
        gc.fillOval(world.getPuck().getPosition().x, world.getPuck().getPosition().y, world.getField().getPuckSize(), world.getField().getPuckSize());        
    }
    
    /**
     * Draws an individual pod. 
     * @param p the Pod object that needs to be drawn.
     * @param c the Color enum of the pod.
     */
    public void drawPod(){
        //temp, note-to-self: avoid hardcoding
            Pod p1 = world.getPod("Player 2");     // AI #1
            Pod p2 = world.getPod("Player 3");     // AI #2
            Body puck = world.getPuck().getBody(); // Puck
            Vec2 p1pos = p1.getPosition();
            Vec2 p2pos = p2.getPosition();
            Vec2 puckpos = puck.getPosition();
            
            //apply force is dependent on gravity
            //apply linearvelocity is one-time only
            //apply impuls doesnt update anything.
            p1.body.setTransform(puckpos, puck.getAngle());
            p2.body.setTransform(puckpos, puck.getAngle());
            
            
            /*  legacy code
            p1.body.applyForce(new Vec2(100,0), puckpos);//todo: set global speed
            //p1.body.applyLinearImpulse(p1.body.getWorldCenter(), puckpos);           
            p2.body.applyForce(new Vec2(0,100), puckpos); //todo: set global speed
            //p2.body.applyLinearImpulse(p2.body.getWorldCenter(), puckpos);           
            System.out.println(puck.getPosition());
            //System.out.println(p1.getPosition());
        //
            */
        //(make sure intial position is initialized before starting this)
        // 1 lookup current position
        // 2 lookup puck position
        // 3 move towards puck.
            // 3.1 involve strategy
        // 4 repeat continously.
    }
    
    @Override
    public void handle(KeyEvent e) {
        goal = world.getField().getGoalCorners(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y, 0, 20);
        switch (e.getCharacter()) {
            case "s":
                gc.setFill(Color.RED);
                if (world.getPod("Henk").getPosition().x > goal.get(2).x) {
                    world.getPod("Henk").move(new Vec2(world.getPod("Henk").getPosition().x -= 5, world.getPod("Henk").getPosition().y));
                }
                break;
            case "d":
                gc.setFill(Color.RED);
                if (world.getPod("Henk").getPosition().x < goal.get(0).x - world.getField().getPodSize()) {
                    world.getPod("Henk").move(new Vec2(world.getPod("Henk").getPosition().x += 5, world.getPod("Henk").getPosition().y));
                }
                break;
        }
    }

    @Override
    public void beginContact(Contact contact) {
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
     * Updates the position of the AI's pods.
     * TODO: Heavy refactoring; only redraw what needs to be redrawn(eg. puck and pods).
     */
    public void updatePodPositions(Object arg){        
        ArrayList<AI> AI_List = (ArrayList<AI>)arg;
        for (AI ai : AI_List){
            Pod p = world.getPod(ai.getName());
            p.move(ai.getDirection());
        }
        Draw();
    }
    
    /**
     * TODO: replace updatePodPositions.
     * @param o 
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        //updatePodPositions(arg);
    }

}
