/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.ChatManager;
import fontys.observer.RemotePropertyListener;
import game.Human;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import networking.standalone.IClientData;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class LobbyController implements Initializable, RemotePropertyListener {

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
    private IClientData clientData;
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
        try {
            UnicastRemoteObject.exportObject(this, 1337);
        } catch (RemoteException ex) {
            System.out.println("Can't export object: " + ex.getMessage());
        }
    }

    /**
     * Method for passing objects from other scenes, call needed to initialize
     * certain GUI objects
     *
     * @param player The player that is logged in on the current session
     * @param lobby The lobby object that this GUI represents
     * @param clientData
     */
    public void initData(Human player, ILobby lobby, IClientData clientData) {
        currentPlayer = player;
        currentLobby = lobby;
        this.clientData = clientData;
        try {
            lobby.addListener(this, null);
            lobby.addPlayer(player);
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }

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
            UnicastRemoteObject.unexportObject(this, true);
            Node node = (Node) evt.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyList.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            LobbyListController controller = loader.<LobbyListController>getController();
            stage.show();
            controller.initData(currentPlayer);
            currentLobby.removePlayer(currentPlayer);
        } catch (IOException ex) {
            System.out.println("Error changing scene from Lobby to LobbyList " + ex.toString());
        }
    }

    /**
     * Update the lobby info, game name, amount of players and host name.
     */
    private void updateLobbyInfo() {
        try {
            if (currentLobby != null && !currentLobby.getAllPlayers().isEmpty()) {
                textGameName.setText(currentLobby.getGameName());
                textPlayerCount.setText(currentLobby.getPlayersAmount() + "/3");
                textHostName.setText(currentLobby.getPlayer(0).getName());
                tablePlayers.setItems(FXCollections.observableArrayList(currentLobby.getAllPlayers()));
                ArrayList<Boolean> readyStates = currentLobby.getPlayerStates();
                //Set all ready states
                for (int i = 0; i < 3; i++) {
                    if (tablePlayers.getItems().size() >= i + 1) {
                        if (readyStates.get(i)) {
                            System.out.println("TODO set ready states in GUI");
                        }
                    }
                }

                //If host ready start game
                System.out.println(readyStates.get(0).toString()+", "+ readyStates.get(1) +", "+  readyStates.get(2));
                if (readyStates.get(0)/* && readyStates.get(1) && readyStates.get(2)*/) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
                        Stage stage = (Stage) chatBox.getScene().getWindow();
                        stage.setScene(new Scene((Pane) loader.load()));
                        GameView controller = loader.<GameView>getController();
                        stage.show();
                        controller.init_Multiplayer(currentPlayer, currentLobby, clientData);
                    } catch (IOException ex) {
                        System.out.println("Error changing scene from Main menu to Settings " + ex.toString());
                    }
                    System.out.println("Should be new window now");
                }
                //Check if chat message is new
                if (chatBox.getItems().size() > 0) {
                    if (!chatBox.getItems().get(chatBox.getItems().size() - 1).toString().equals(currentLobby.getLastChatMessage()) && !currentLobby.getLastChatMessage().isEmpty()) {
                        try {
                            chat.addMessage(currentLobby.getLastChatMessage());
                        } catch (RemoteException ex) {
                            Logger.getLogger(LobbyController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    chat.addMessage("Welcome to the game!");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Event handler if 'Send' button is pressed
     *
     * @param evt
     */
    public void sendMessage(ActionEvent evt) {
        if (!chatMessage.getText().equals("")) {
            try {
                currentLobby.setLastChatMessage(currentPlayer.getName() + ": " + chatMessage.getText());
            } catch (Exception ex) {
                System.out.println("Set chat message failed");
                System.out.println(ex.getMessage());
            }
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
            try {
                readyButton.getStyleClass().remove("ready");
                currentLobby.setPlayerState(currentLobby.getAllPlayers().indexOf(currentPlayer), false);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                readyButton.getStyleClass().add("ready");
                currentLobby.setPlayerState(currentLobby.getAllPlayers().indexOf(currentPlayer), true);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
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
            Logger.getLogger(LobbyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method for switching to the GameView scene
     *
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
            controller.init_Multiplayer(currentPlayer, currentLobby, clientData);
        } catch (IOException ex) {
            System.out.println("Error changing scene from Lobby to Game " + ex.toString());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                updateLobbyInfo();
            }
        });
    }
}
