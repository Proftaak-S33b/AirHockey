/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
import javafx.scene.image.Image;

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

        
    }
    
    public void SetTekst(ActionEvent event)
    {
        listChat.getItems().add(textChat.getText());
        textChat.clear();
    }
    
    
}
