/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
import javafx.stage.StageStyle;
import networking.standalone_old.IClientData;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

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

    private Human currentPlayer;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new LobbyManager(chatBox, currentPlayer);
        columnGameName.setCellValueFactory(new PropertyValueFactory("name"));
        columnPlayers.setCellValueFactory(new PropertyValueFactory("playerAmount"));
        columnHostRank.setCellValueFactory(new PropertyValueFactory("ranking"));
        lobbyTable.setItems(controller.getLobbies());
    }

    /**
     *
     * @param currentPlayer
     */
    public void initData(Human currentPlayer) {
        this.currentPlayer = currentPlayer;
        Stage stage = (Stage) lobbyTable.getScene().getWindow();
        stage.setTitle("Lobbies - Hello, " + currentPlayer.getName());
    }

    /**
     *
     * @param evt
     */
    public void backButton(ActionEvent evt) {
        try {
            controller.destroy();
            Node node = (Node) evt.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error changing scene from LobbyList to MainMenu " + ex.toString());
        }
    }

    /**
     *
     * @param evt
     */
    public void createLobby(Event evt) {
        try {
            controller.destroy();
            Node node = (Node) evt.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            LobbyController lobbyFXML = loader.<LobbyController>getController();
            lobbyFXML.initData(currentPlayer, controller.addLobby(newLobbyName.getText(), currentPlayer), null);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error changing scene from LobbyList to Lobby " + ex.toString());
        }
    }

    /**
     *
     * @param evt
     */
    public void joinLobby(Event evt) {
        if (lobbyTable.getSelectionModel().getSelectedItem() instanceof IClientData) {
            try {
                IClientData tempdata = (IClientData) lobbyTable.getSelectionModel().getSelectedItem();
                if (tempdata.getPlayerAmount() < 3) {
                    controller.serverData.setPlayerCountLobby(tempdata.getHost(), tempdata.getPlayerAmount() + 1);
                    controller.destroy();
                    Node node = (Node) evt.getSource();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setScene(new Scene((Pane) loader.load()));
                    LobbyController lobbyFXML = loader.<LobbyController>getController();
                    lobbyFXML.initData(currentPlayer, controller.connect((IClientData) lobbyTable.getSelectionModel().getSelectedItem()), tempdata);
                    stage.show();
                } else {
                    Action response = Dialogs.create()
                    .owner(false ? this : null)
                    .title("ERROR!")
                    .masthead(false ? "Error!" : null)
                    .message("Lobby is full")
                    .showError();
                }
            } catch (IOException ex) {
                System.out.println("Error changing scene from LobbyList to Lobby " + ex.toString());
            }
        }
    }

    /**
     *
     * @param evt
     */
    public void spectLobby(Event evt) {
        
    }

    /**
     *
     * @param event
     */
    public void sendChat(ActionEvent event) {
        if (!chatMessage.getText().equals("")) {
            controller.sendChat(currentPlayer.getName() + ": " + chatMessage.getText().trim());
            chatMessage.clear();
        }
    }

    /**
     * Opens the leaderboard window
     *
     * @param event
     */
    public void buttonLeaderboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Leaderboards.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error changing scene from lobby to leaderboards " + ex.toString());
        }
    }
}
