/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import game.*;
import java.net.URL;
import java.util.ArrayList;
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

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class FXMLGameController implements Initializable, EventHandler<KeyEvent> {

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
    private Double xPod;
    private Double yPod;
    private Double xPuck;
    private Double yPuck;
    ArrayList<Coordinate> corners;
    ArrayList<Coordinate> goal;
    ArrayList<Coordinate> pod;
    Puck p;

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
        gc = gameCanvas.getGraphicsContext2D();
        corners = world.getField().getFieldCorners();
        pod = world.getField().getStartPositions();
        xPod = pod.get(2).x;
        yPod = pod.get(2).y;
        p = new Puck(1);
        xPuck = p.getPosition().x;
        yPuck = p.getPosition().y;
        Draw();

    }

    public void SetTekst(ActionEvent event) {
        listChat.getItems().add(textChat.getText());
        textChat.clear();
    }

    public void Draw() {
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0.0, 0.0, 550, 550);

        //Blue side
        gc.setStroke(Color.BLUE);
        gc.strokeLine(corners.get(0).x, corners.get(0).y, corners.get(1).x, corners.get(1).y);
        goal = world.getField().getGoalCorners(corners.get(0).x, corners.get(0).y, corners.get(1).x, corners.get(1).y, -18, -12);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.setFill(Color.BLUE);
        gc.fillOval(pod.get(0).x, pod.get(0).y, world.getField().getPodSize(), world.getField().getPodSize());

        //Green side
        gc.setStroke(Color.GREEN);
        gc.strokeLine(corners.get(1).x, corners.get(1).y, corners.get(2).x, corners.get(2).y);
        goal = world.getField().getGoalCorners(corners.get(1).x, corners.get(1).y, corners.get(2).x, corners.get(2).y, 18, -12);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.setFill(Color.GREEN);
        gc.fillOval(pod.get(1).x, pod.get(1).y, world.getField().getPodSize(), world.getField().getPodSize());

        //Red side
        gc.setStroke(Color.RED);
        gc.strokeLine(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y);
        goal = world.getField().getGoalCorners(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y, 0, 20);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.setFill(Color.RED);
        gc.fillOval(xPod, yPod, world.getField().getPodSize(), world.getField().getPodSize());

        //puck
        gc.setFill(Color.BLACK);
        gc.fillOval(xPuck, yPuck, world.getField().getPuckSize(), world.getField().getPuckSize());

    }

    @Override
    public void handle(KeyEvent e) {
        goal = world.getField().getGoalCorners(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y, 0, 20);
        switch (e.getCharacter()) {
            case "s":
                gc.setFill(Color.RED);
                if (xPod > goal.get(2).x) {
                    xPod -= 5;
                    Draw();
                }
                break;
            case "d":
                gc.setFill(Color.RED);
                if (xPod < goal.get(0).x - world.getField().getPodSize()) {
                    xPod += 5;
                    Draw();
                }
                break;
        }
    }

}
