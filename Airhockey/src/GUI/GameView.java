package GUI;

//<editor-fold defaultstate="collapsed" desc="imports">
import game.Human;
import game.MathUtillities;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import networking.IPlayer;
import networking.Lobby;
import org.jbox2d.common.Vec2;

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class GameView implements Initializable {

    private enum GameType {

        SINGLEPLAYER,
        MULTIPLAYER
    }

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

    private GraphicsContext gc;

    //Singleplayer
    private IPlayer currentPlayer;
    //Multiplayer
    private Lobby currentLobby;
    private GameType gametype;
    
    //Movement commands
    private boolean playerMoveRight;
    private boolean playerMoveLeft;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = gameCanvas.getGraphicsContext2D();
        draw();

    }

    public void init_Singleplayer(Human player) {
        currentPlayer = player;
        gametype = GameType.SINGLEPLAYER;
    }

    public void init_Multiplayer(Human player, Lobby lobby) {
        currentPlayer = player;
        currentLobby = lobby;
        gametype = GameType.MULTIPLAYER;
    }

    /**
     * Draws the sides and puck on the field.
     */
    public void draw() {
        try {

            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(0.0, 0.0, 550, 550);

            //Blue side
            drawSide(Color.BLUE, "Player 2");

            //Green side
            drawSide(Color.GREEN, "Player 3");

            //Red side
            drawSide(Color.RED, "Henk");

            drawPuck();

            drawPod();
            
            player_Move();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Draws a given side. Refactored from Draw().
     * @param color
     * @param playername
     */
    public void drawSide(Color color, String playername) {
        gc.setStroke(color);
        gc.setFill(color);
        
        // fieldcorners
        Vec2 field_bottomleft = Convert(MathUtillities.getFieldCorners().get(0));
        Vec2 field_top = Convert(MathUtillities.getFieldCorners().get(1));
        Vec2 field_bottomright = Convert(MathUtillities.getFieldCorners().get(2));
        
        // goalcorners / sides
        Vec2 goal_bottomleft = Convert(MathUtillities.getGoalCorners().get(0));
        Vec2 goal_bottomright = Convert(MathUtillities.getGoalCorners().get(1));
        Vec2 goal_lefttop = Convert(MathUtillities.getGoalCorners().get(2));
        Vec2 goal_leftbottom = Convert(MathUtillities.getGoalCorners().get(3));
        Vec2 goal_rightbottom = Convert(MathUtillities.getGoalCorners().get(4));
        Vec2 goal_righttop = Convert(MathUtillities.getGoalCorners().get(5));
        
        if(color == Color.RED){
        gc.strokeLine(
                field_bottomleft.x,                
                field_bottomleft.y,
                                
                goal_bottomleft.x,                
                goal_bottomleft.y);
        
        gc.strokeLine(
                field_bottomright.x,
                field_bottomright.y,
                
                goal_bottomright.x,
                goal_bottomright.y);
        
         gc.strokeLine(
                goal_bottomleft.x,                
                goal_bottomleft.y,
                
                goal_bottomright.x,
                goal_bottomright.y);
        }
        if(color == Color.BLUE){
        gc.strokeLine(
                
                field_top.x,
                field_top.y,
                
                goal_lefttop.x,
                goal_lefttop.y);
                
        gc.strokeLine(
                field_bottomleft.x,
                field_bottomleft.y,
                
                goal_leftbottom.x,
                goal_leftbottom.y);
        
        gc.strokeLine(
                goal_lefttop.x,
                goal_lefttop.y,
                
                goal_leftbottom.x,
                goal_leftbottom.y);
        }
        if(color == Color.GREEN){
        gc.strokeLine(
                
                field_bottomright.x,
                field_bottomright.y,
                
                goal_rightbottom.x,
                goal_rightbottom.y);
        
        gc.strokeLine(
                field_top.x,
                field_top.y,
                                
                goal_righttop.x,
                goal_righttop.y);
        
        gc.strokeLine(
                goal_rightbottom.x,
                goal_rightbottom.y,
                                
                goal_righttop.x,
                goal_righttop.y);
        }
    }

    /**
     * Scales the vector and rotates the Y-coordinate.
     * @param vector a Vec2 object to convert.
     */
    private Vec2 Convert(Vec2 vector){
        
        // Scales the physics to the drawing.
        final int scale = 10;
        
        vector.x *= scale;
        vector.y = MathUtillities.rotateVector(vector).y * scale;
        
        return vector;
    }
    
    /**
     * Draws the puck. Refactored from Draw().
     */
    public void drawPuck() {
        gc.setFill(Color.BLACK);
        //gc.fillOval(pm.getPuckPosition().x, pm.getPuckPosition().y, pm.getPuckSize(), pm.getPuckSize());
    }

    /**
     * Draws an individual pod.
     */
    public void drawPod() {
        ArrayList<Vec2> corners = MathUtillities.getGoalCorners();

        float aX = corners.get(0).x;
        float aY = corners.get(0).y;
        float bX = corners.get(1).x;
        float bY = corners.get(1).y;
        // AI
        //AI_CalculateMovement(corners);
    }

    /**
     * Part of slowly phasing out the AI from the Controller to the AI classes.
     */
    private void AI_CalculateMovement() {

    }

    /**
     * Moves the AI up from player viewpoint.
     */
    private void AI_moveUp() {

    }

    /**
     * Moves the AI down from player viewpoint.
     */
    private void AI_moveDown() {

    }

    /**
     * Method for handling send chat button click
     *
     * @param event
     */
    public void setTekst(ActionEvent event) {
        if (!textChat.getText().equals("")) {
            listChat.getItems().add(textChat.getText());
            textChat.clear();
        }
    }

    public void exit(ActionEvent event) {

    }
    
    /**
     * Checks which buttons are pressed and moves the player
     */
    private void player_Move() {
        if (playerMoveRight)
        {
            //if (pm.getPodPosition(0).x > goalCoordinates.get(2).x) {
            //    pm.movePodLeft(0);
            //}
        }
        if(playerMoveLeft){
            //if (pm.getPodPosition(0).x < goalCoordinates.get(0).x - 5 - pm.getPodSize() ) {
            //    pm.movePodRight(0);
            //}
        }
    }
    /**
     * Checks if a key is pressed and adjusts the player movement.
     * @param event the keyevent that happened
     */
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                if (playerMoveRight) {
                    playerMoveRight = false;
                }
                playerMoveLeft = true;
                break;
            case RIGHT:
                if(playerMoveLeft) {
                    playerMoveLeft = false;
                }
                playerMoveRight = true;
                break;
        }
    }
    
    /**
     * Checks if a key is released and adjusts the player movement.
     * @param event the keyevent that happened
     */
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                if(playerMoveLeft) {
                    playerMoveLeft = false;
                }
                break;
            case RIGHT:
                if(playerMoveRight) {
                    playerMoveRight = false;
                }
                break;
        }
    }
}
