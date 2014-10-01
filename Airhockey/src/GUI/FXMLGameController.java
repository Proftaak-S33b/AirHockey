/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import game.Coordinate;
import static java.lang.Math.*;
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

    //TEMPORARY FOR TESTING PURPOSES
    private final int marge = 25;
    private final int fieldSize = 500;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //columnPlayer.setCellValueFactory(new );
        
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        
        Image img = new Image("Speelveld.png");
        gc.drawImage(img, -200, -200);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        ArrayList<Coordinate> corners;

        //Blue side
        gc.setStroke(Color.BLUE);
        gc.strokeLine(fieldSize / 2 + marge, 67 + marge, 0, fieldSize + marge);
        corners = getGoalCorners(fieldSize / 2 + marge, 67 + marge, 0, fieldSize + marge, -20, -14);
        gc.strokePolygon(new double[]{corners.get(0).x, corners.get(1).x, corners.get(2).x, corners.get(3).x},
                new double[]{corners.get(0).y, corners.get(1).y, corners.get(2).y, corners.get(3).y}, 4);

        //Green side
        gc.setStroke(Color.GREEN);
        gc.strokeLine(fieldSize / 2 + marge, 67 + marge, fieldSize + marge, fieldSize + marge);
        corners = getGoalCorners(fieldSize / 2 + marge, 67 + marge, fieldSize + marge, fieldSize + marge, 20, -14);
        gc.strokePolygon(new double[]{corners.get(0).x, corners.get(1).x, corners.get(2).x, corners.get(3).x},
                new double[]{corners.get(0).y, corners.get(1).y, corners.get(2).y, corners.get(3).y}, 4);

        //Red side
        gc.setStroke(Color.RED);
        gc.strokeLine(0, fieldSize + marge, fieldSize + marge, fieldSize + marge);
        corners = getGoalCorners(0, fieldSize + marge, fieldSize + marge, fieldSize + marge, 0, 20);
        gc.strokePolygon(new double[]{corners.get(0).x, corners.get(1).x, corners.get(2).x, corners.get(3).x},
                new double[]{corners.get(0).y, corners.get(1).y, corners.get(2).y, corners.get(3).y}, 4);

    }

    private ArrayList<Coordinate> getGoalCorners(double x1, double y1, double x2, double y2, double margeX, double margeY) {
        ArrayList<Coordinate> rectangleGoal = new ArrayList<>();
        double vx = x2 - x1; // x vector
        double vy = y2 - y1; // y vector
        double mag = sqrt(vx * vx + vy * vy); //magnitude (also known as length)
        vx /= mag; //normalize x vector
        vy /= mag; //normalize y vector

        rectangleGoal.add(new Coordinate((int) ((float) x1 + vx * (mag * 0.3)), (int) ((float) y1 + vy * (mag * 0.3))));
        rectangleGoal.add(new Coordinate((int) ((float) x1 + margeX + vx * (mag * 0.3)), (int) ((float) y1 + margeY + vy * (mag * 0.3))));
        rectangleGoal.add(new Coordinate((int) ((float) x1 + margeX + vx * (mag * 0.7)), (int) ((float) y1 + margeY + vy * (mag * 0.7))));
        rectangleGoal.add(new Coordinate((int) ((float) x1 + vx * (mag * 0.7)), (int) ((float) y1 + vy * (mag * 0.7))));
        return rectangleGoal;
    }

    public void SetTekst(ActionEvent event) {
        listChat.getItems().add(textChat.getText());
        textChat.clear();
    }
    
    
}
