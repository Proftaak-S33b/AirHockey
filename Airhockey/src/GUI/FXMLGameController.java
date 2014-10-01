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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author HP user
 */
public class FXMLGameController implements Initializable {

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

        //Get data for drawing
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        ArrayList<Coordinate> corners = world.getField().getFieldCorners();
        ArrayList<Coordinate> goal;
        ArrayList<Coordinate> puck = world.getField().getStartPositions();
        
        //Blue side
        gc.setStroke(Color.BLUE);
        gc.strokeLine(corners.get(0).x, corners.get(0).y, corners.get(1).x, corners.get(1).y);
        goal = world.getField().getGoalCorners(corners.get(0).x, corners.get(0).y, corners.get(1).x, corners.get(1).y, -18, -12);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.fillOval(puck.get(0).x , puck.get(0).y, world.getField().getPodSize(), world.getField().getPodSize());

        //Green side
        gc.setStroke(Color.GREEN);
        gc.strokeLine(corners.get(1).x, corners.get(1).y, corners.get(2).x, corners.get(2).y);
        goal = world.getField().getGoalCorners(corners.get(1).x, corners.get(1).y, corners.get(2).x, corners.get(2).y, 18, -12);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.fillOval(puck.get(1).x, puck.get(1).y, 500 * 0.08, 500 * 0.08);

        //Red side
        gc.setStroke(Color.RED);
        gc.strokeLine(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y);
        goal = world.getField().getGoalCorners(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y, 0, 20);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.fillOval(puck.get(2).x, puck.get(2).y, 500 * 0.08, 500 * 0.08);
    }

    public void SetTekst(ActionEvent event) {
        listChat.getItems().add(textChat.getText());
        textChat.clear();
    }

}
