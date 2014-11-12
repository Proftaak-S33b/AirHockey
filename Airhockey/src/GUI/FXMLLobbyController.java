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
import networking.Lobby;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class FXMLLobbyController implements Initializable {

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
    private Lobby currentLobby;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initData(Human player, Lobby lobby) {
        currentPlayer = player;
        currentLobby = lobby;
        Stage stage = (Stage) tablePlayers.getScene().getWindow();
        stage.setTitle(currentLobby.getGameName());
    }

    public void sendMessage(ActionEvent evt) {

    }

    public void startGame(ActionEvent evt) {

    }
}
