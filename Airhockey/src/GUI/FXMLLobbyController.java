/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import game.Human;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class FXMLLobbyController implements Initializable {

    @FXML
    Parent root;

    @FXML
    public Label labelHostName;

    @FXML
    public Label labelGameName;

    @FXML
    public ListView chatBox;

    @FXML
    public TableView tablePlayers;

    @FXML
    public TableColumn columnPlayers;

    @FXML
    public TableColumn columnRanking;

    @FXML
    public Label labelPlayerCount;

    @FXML
    public TextField tfChatMessage;

    private Human currentPlayer;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void initData(Human currentplayer) {
        this.currentPlayer = currentplayer;
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setTitle("Lobbies - Hello, " + currentPlayer.getName());
    }

    public void sendMessage(ActionEvent evt) {
        
    }

    public void startGame(ActionEvent evt) {
        
    }
}
