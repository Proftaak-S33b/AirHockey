/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.lobbyController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Joris
 */
public class FXMLLobbyListController implements Initializable {

    @FXML
    public TableView lobbyTable;

    @FXML
    public TableColumn columnGameName;

    @FXML
    public TableColumn columnPlayers;

    @FXML
    public TableColumn columnHostRank;

    private lobbyController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new lobbyController();
        columnGameName.setCellValueFactory(new PropertyValueFactory("gameName"));
        columnPlayers.setCellValueFactory(new PropertyValueFactory("playersAmount"));
        columnHostRank.setCellValueFactory(new PropertyValueFactory("hostScore"));
        lobbyTable.setItems(controller.getLobbies());

    }

    public void selectGame(Event evt) {

    }

    public void createLobby(Event evt) {

    }

    public void joinLobby(Event evt) {

    }

    public void spectLobby(Event evt) {

    }

    public void sendChat(Event evt) {

    }
}
