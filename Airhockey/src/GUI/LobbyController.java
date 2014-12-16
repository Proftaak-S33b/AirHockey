/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.ChatManager;
import game.Human;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import networking.ILobby;
import networking.Lobby;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class LobbyController implements Initializable {

    @FXML
    public TextField textHostName;

    @FXML
    public TextField textGameName;

    @FXML
    public TextField textPlayerCount;

    @FXML
    public ListView chatBox;

    @FXML
    public TableView tablePlayers;

    @FXML
    public TableColumn columnPlayers;

    @FXML
    public TableColumn columnRanking;

    @FXML
    public TextField chatMessage;

    @FXML
    public Button readyButton;

    private Human currentPlayer;
    private ILobby currentLobby;
    private boolean ready = false;

    private ChatManager chat;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize chat
        chat = new ChatManager();
        chatBox.setItems(chat.getMessages());
    }

    /**
     * Method for passing objects from other scenes, call needed to initialize
     * certain GUI objects
     *
     * @param player The player that is logged in on the current session
     * @param lobby The lobby object that this GUI represents
     */
    public void initData(Human player, ILobby lobby) {
        currentPlayer = player;
        currentLobby = lobby;

        //Set stage title
        Stage stage = (Stage) tablePlayers.getScene().getWindow();
        try {
            stage.setTitle(currentLobby.getGameName());
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }

        //Set lobby info text fields
        updateLobbyInfo();

        //Initialize players table
        columnPlayers.setCellValueFactory(new PropertyValueFactory("name"));
        columnRanking.setCellValueFactory(new PropertyValueFactory("ranking"));
    }

    /**
     * Event handler if 'Back' button is pressed
     *
     * @param evt
     */
    public void backButton(ActionEvent evt) {
        try {
            Node node = (Node) evt.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyList.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            LobbyListController controller = loader.<LobbyListController>getController();
            stage.show();
            controller.initData(currentPlayer);
        } catch (IOException ex) {
            System.out.println("Error changing scene from Lobby to LobbyList " + ex.toString());
        }
    }

    /**
     * Update the lobby info, game name, amount of players and host name.
     */
    private void updateLobbyInfo() {
        if (currentLobby != null) {
            try {
                textGameName.setText(currentLobby.getGameName());
                textPlayerCount.setText(currentLobby.getPlayersAmount() + "/3");
                textHostName.setText(currentLobby.getPlayer(0).getName());
                tablePlayers.setItems(FXCollections.observableArrayList(currentLobby.getAllPlayers()));
            } catch (RemoteException ex) {
                System.out.println("RemoteException: " + ex.getMessage());
            }
        }
    }

    /**
     * Event handler if 'Send' button is pressed
     *
     * @param evt
     */
    public void sendMessage(ActionEvent evt) {
        if (!chatMessage.getText().equals("")) {
            chat.addMessage(chatMessage.getText(), currentPlayer);
            chatMessage.clear();
        }
    }

    /**
     * Event handler if 'Ready' button is pressed,
     *
     * @param evt
     */
    public void readyButton(ActionEvent evt) {
        if (ready) {
            readyButton.getStyleClass().remove("ready");
        } else {
            readyButton.getStyleClass().add("ready");
        }
        ready = !ready;
    }

    /**
     * Event handler if ENTER key was pressed when focus was in textGameName
     * field.
     *
     * @param evt
     */
    public void changedName(ActionEvent evt) {
        try {
            currentLobby.setGameName(textGameName.getText());
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method for switching to the GameView scene
     * @param evt
     */
    public void startGame(ActionEvent evt) {
        try {
            Node node = (Node) evt.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            GameView controller = loader.<GameView>getController();
            stage.show();
            controller.init_Multiplayer(currentPlayer, currentLobby);
        } catch (IOException ex) {
            System.out.println("Error changing scene from Lobby to Game " + ex.toString());
        }
    }
}
