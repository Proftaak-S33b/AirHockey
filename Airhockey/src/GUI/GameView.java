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
        if(color == Color.RED){
        gc.strokeLine(MathUtillities.getFieldCorners().get(0).x * 10,
                MathUtillities.rotateVector(MathUtillities.getFieldCorners().get(0)).y * 10, 
                MathUtillities.getGoalCorners().get(0).x * 10, 
                MathUtillities.rotateVector(MathUtillities.getGoalCorners().get(0)).y* 10) ;
        gc.strokeLine(MathUtillities.getFieldCorners().get(2).x * 10,
                MathUtillities.rotateVector(MathUtillities.getFieldCorners().get(2)).y * 10, 
                MathUtillities.getGoalCorners().get(1).x * 10, 
                MathUtillities.rotateVector(MathUtillities.getGoalCorners().get(1)).y* 10) ;
        }
        if(color == Color.BLUE){
        gc.strokeLine(MathUtillities.getFieldCorners().get(0).x * 10,
                MathUtillities.rotateVector(MathUtillities.getFieldCorners().get(0)).y * 10, 
                MathUtillities.getGoalCorners().get(3).x * 10, 
                MathUtillities.rotateVector(MathUtillities.getGoalCorners().get(3)).y* 10) ;
        gc.strokeLine(MathUtillities.getFieldCorners().get(1).x * 10,
                MathUtillities.rotateVector(MathUtillities.getFieldCorners().get(1)).y * 10, 
                MathUtillities.getGoalCorners().get(2).x * 10, 
                MathUtillities.rotateVector(MathUtillities.getGoalCorners().get(2)).y* 10) ;
        }
        if(color == Color.GREEN){
        gc.strokeLine(MathUtillities.getFieldCorners().get(1).x * 10,
                MathUtillities.rotateVector(MathUtillities.getFieldCorners().get(1)).y * 10, 
                MathUtillities.getGoalCorners().get(5).x * 10, 
                MathUtillities.rotateVector(MathUtillities.getGoalCorners().get(5)).y* 10) ;
        gc.strokeLine(MathUtillities.getFieldCorners().get(2).x * 10,
                MathUtillities.rotateVector(MathUtillities.getFieldCorners().get(2)).y * 10, 
                MathUtillities.getGoalCorners().get(4).x * 10, 
                MathUtillities.rotateVector(MathUtillities.getGoalCorners().get(4)).y* 10) ;
        }
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
