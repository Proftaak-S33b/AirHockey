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
import org.jbox2d.common.Vec2;

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
    ArrayList<Vec2> corners;
    ArrayList<Vec2> goal;

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
        Draw();
        
        AnimationTimer at = new AnimationTimer() {

            @Override
            public void handle(long now) {
                Draw();
               gc.setFill(Color.BLACK);
                gc.fillOval(world.getPuck().getPosition().x, world.getPuck().getPosition().y, world.getField().getPuckSize(), world.getField().getPuckSize());
            }
        };
        at.start();
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
        gc.fillOval(world.getPod("Player 2").getPosition().x, world.getPod("Player 2").getPosition().y, world.getField().getPodSize(), world.getField().getPodSize());

        //Green side
        gc.setStroke(Color.GREEN);
        gc.strokeLine(corners.get(1).x, corners.get(1).y, corners.get(2).x, corners.get(2).y);
        goal = world.getField().getGoalCorners(corners.get(1).x, corners.get(1).y, corners.get(2).x, corners.get(2).y, 18, -12);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.setFill(Color.GREEN);
        gc.fillOval(world.getPod("Player 3").getPosition().x, world.getPod("Player 3").getPosition().y, world.getField().getPodSize(), world.getField().getPodSize());

        //Red side
        gc.setStroke(Color.RED);
        gc.strokeLine(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y);
        goal = world.getField().getGoalCorners(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y, 0, 20);
        gc.strokePolygon(new double[]{goal.get(0).x, goal.get(1).x, goal.get(2).x, goal.get(3).x},
                new double[]{goal.get(0).y, goal.get(1).y, goal.get(2).y, goal.get(3).y}, 4);
        gc.setFill(Color.RED);
        gc.fillOval(world.getPod("Henk").getPosition().x, world.getPod("Henk").getPosition().y, world.getField().getPodSize(), world.getField().getPodSize());

        //puck
        gc.setFill(Color.BLACK);
        gc.fillOval(world.getPuck().getPosition().x, world.getPuck().getPosition().y, world.getField().getPuckSize(), world.getField().getPuckSize());

    }

    @Override
    public void handle(KeyEvent e) {
        goal = world.getField().getGoalCorners(corners.get(2).x, corners.get(2).y, corners.get(0).x, corners.get(0).y, 0, 20);
        switch (e.getCharacter()) {
            case "s":
                gc.setFill(Color.RED);
                System.out.println(goal.get(2).x);
                System.out.println(world.getPod("Henk").getPosition().x);
                if (world.getPod("Henk").getPosition().x > goal.get(2).x) {
                    world.getPod("Henk").move(new Vec2(world.getPod("Henk").getPosition().x -=5, world.getPod("Henk").getPosition().y));
                }
                break;
            case "d":
                gc.setFill(Color.RED);
                if (world.getPod("Henk").getPosition().x < goal.get(0).x - world.getField().getPodSize()) {
                    world.getPod("Henk").move(new Vec2(world.getPod("Henk").getPosition().x +=5, world.getPod("Henk").getPosition().y));
                }
                break;
        }
    }

}
