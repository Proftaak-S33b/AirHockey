package GUI;

//<editor-fold defaultstate="collapsed" desc="imports">
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

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class FXMLGameView implements Initializable, EventHandler<KeyEvent> {

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

    GraphicsContext gc;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = gameCanvas.getGraphicsContext2D();

    }

       /**
     * Draws the sides and puck on the field.
     */
    public void Draw() {
        
    } 
    
    /**
     * Draws a given side. Refactored from Draw().
     */
     public void drawSide(){
         
     }
     
    /**
     * Draws the puck. Refactored from Draw().
     */
    public void drawPuck() {
        
    }
    
     /**
     * Draws an individual pod.
     */
    public void drawPod() {
        
    }
    
     /**
     * Part of slowly phasing out the AI from the Controller to the AI classes.
     */
     private void AI_CalculateMovement(){
         
     }
     
    /**
    * Moves the AI up from player viewpoint.
    */
    private void AI_moveUp(){
        
    }
    
     /**
     * Moves the AI down from player viewpoint.
     */
    private void AI_moveDown(){
        
    }
            
    /**
     * Method for handling send chat button click
     *
     * @param event
     */
    public void SetTekst(ActionEvent event) {
        if(!textChat.getText().equals(""))
        {
            listChat.getItems().add(textChat.getText());
            textChat.clear();
        }
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
