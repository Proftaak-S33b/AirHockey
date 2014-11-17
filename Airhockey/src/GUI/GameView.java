package GUI;

//<editor-fold defaultstate="collapsed" desc="imports">
import game.Human;
import game.MathUtillities;
import java.net.URL;
import java.util.ResourceBundle;
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
import networking.IPlayer;
import networking.Lobby;

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class GameView implements Initializable, EventHandler<KeyEvent> {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = gameCanvas.getGraphicsContext2D();

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
            //drawSide(/*Color.BLUE, "Player 2", 0, 1, -18, -12*/);

            //Green side
            //drawSide(/*Color.GREEN, "Player 3", 1, 2, 18, -12*/);

            //Red side
            drawSide(Color.RED, "Henk");

            drawPuck();

            drawPod();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Draws a given side. Refactored from Draw().
     */
    public void drawSide(Color color, String playername) {
        double[] goalXcoords = new double[4];
        double[] goalYcoords = new double[4];
            goalXcoords[0] = MathUtillities.getFieldCorners().get(0).x * 10;
            goalXcoords[1] = MathUtillities.getGoalCorners().get(0).x * 10;
            goalYcoords[0] = MathUtillities.getFieldCorners().get(0).y * 10;
            goalYcoords[1] = MathUtillities.getGoalCorners().get(0).y * 10;
            goalXcoords[2] = MathUtillities.getFieldCorners().get(0).x * 10;
            goalXcoords[3] = MathUtillities.getGoalCorners().get(0).x * 10;
            goalYcoords[2] = (MathUtillities.getFieldCorners().get(0).y - 3) * 10;
            goalYcoords[3] = (MathUtillities.getGoalCorners().get(0).y - 3) * 10;
        if (color == Color.RED)
        {
            gc.strokePolygon(goalXcoords, goalYcoords, 4);
            gc.setFill(color);
        }
        /*ArrayList<Vec2> corners = pm.getFieldCorners();

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
        }*/
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
        //ArrayList<Vec2> corners = pm.getFieldCorners();

//        float aX = corners.get(0).x;
//        float aY = corners.get(0).y;
//        float bX = corners.get(1).x;
//        float bY = corners.get(1).y;
        //ArrayList<Vec2> goalCoordinates = pm.getGoalCorners(aX, aY, bX, bY, -18, -12);
        // AI
        AI_CalculateMovement(/*goalCoordinates*/);
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

    @Override
    public void handle(KeyEvent event) {

        switch (event.getCode()) {
            case LEFT:
                //if (pm.getPodPosition(0).x > goalCoordinates.get(2).x) {
                //    pm.movePodLeft(0);
                //}
                break;
            case RIGHT:
                //if (pm.getPodPosition(0).x < goalCoordinates.get(0).x - 5 - pm.getPodSize() ) {
                //    pm.movePodRight(0);
                //}
                break;
        }
    }
}
