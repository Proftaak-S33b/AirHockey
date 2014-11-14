/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.ChatManager;
import controllers.LobbyManager;
import game.Human;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Joris
 */
public class LobbyListController implements Initializable {

    @FXML
    public TableView lobbyTable;

    @FXML
    public TableColumn columnGameName;

    @FXML
    public TableColumn columnPlayers;

    @FXML
    public TableColumn columnHostRank;

    @FXML
    public TextField newLobbyName;

    @FXML
    public TextField chatMessage;

    @FXML
    public ListView chatBox;

    private LobbyManager controller;
    private ChatManager chat;

    private Human currentPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new LobbyManager();
        chat = new ChatManager();
        chatBox.setItems(chat.getMessages());
        columnGameName.setCellValueFactory(new PropertyValueFactory("gameName"));
        columnPlayers.setCellValueFactory(new PropertyValueFactory("playersAmount"));
        columnHostRank.setCellValueFactory(new PropertyValueFactory("hostRank"));
        lobbyTable.setItems(controller.getLobbies());
        controller.addLobby("Test1", new Human("henk", "test", 50));
    }

    public void initData(Human currentPlayer) {
        this.currentPlayer = currentPlayer;
        Stage stage = (Stage) lobbyTable.getScene().getWindow();
        stage.setTitle("Lobbies - Hello, " + currentPlayer.getName());
    }

    public void selectGame(Event evt) {

    }

    public void createLobby(Event evt) {
        controller.addLobby(newLobbyName.getText(), currentPlayer);
        try {
            Node node = (Node) evt.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            LobbyController lobbyFXML = loader.<LobbyController>getController();
            lobbyFXML.initData(currentPlayer, controller.getLobby(newLobbyName.getText()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error changing scene from LobbyList to Lobby " + ex.toString());
        }
    }

    public void joinLobby(Event evt) {

    }

    public void spectLobby(Event evt) {

    }
    
    public void sendChat(ActionEvent event) {
        if(!chatMessage.getText().equals(""))
        {
            chat.addMessage(chatMessage.getText(), currentPlayer);
            chatMessage.clear();
        }
    }
}