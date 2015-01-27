/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import game.Human;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
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
import networking.sockets.Client;
import networking.standalone.Lobby;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class LobbyController implements Initializable, Observer {

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
    private Lobby currentLobby;
    private Client client;
    private boolean ready = false;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Method for passing objects from other scenes, call needed to initialize
     * certain GUI objects
     *
     * @param player The player that is logged in on the current session
     * @param lobby The lobby object that this GUI represents
     * @param client
     */
    public void initData(Human player, Lobby lobby, Client client) {
        currentPlayer = player;
        currentLobby = lobby;
        this.client = client;
        currentLobby.addPlayer(player);
        this.client.addObserver(this);

        //Set stage title
        Stage stage = (Stage) tablePlayers.getScene().getWindow();
        stage.setTitle(currentLobby.getGameName());

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
            currentLobby.removePlayer(currentPlayer);
        } catch (IOException ex) {
            System.out.println("Error changing scene from Lobby to LobbyList " + ex.toString());
        }
    }

    /**
     * Update the lobby info, game name, amount of players and host name.
     */
    private void updateLobbyInfo() {
        System.out.println("Update lobby info: " + currentLobby.getPlayersAmount());
        if (currentLobby != null && !currentLobby.getAllPlayers().isEmpty()) {
            textGameName.setText(currentLobby.getGameName());
            textPlayerCount.setText(currentLobby.getPlayersAmount() + "/3");
            textHostName.setText(currentLobby.getPlayer(0).getName());
            tablePlayers.setItems(FXCollections.observableArrayList(currentLobby.getAllPlayers()));
            ArrayList<Boolean> readyStates = (ArrayList<Boolean>) currentLobby.getPlayerStates();
            //Set all ready states
            for (int i = 0; i < 3; i++) {
                if (tablePlayers.getItems().size() >= i + 1) {
                    if (readyStates.get(i)) {
                        System.out.println("TODO set ready states in GUI");
                    }
                }
            }

            //If host ready start game
            if (ready) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
                    Stage stage = (Stage) chatBox.getScene().getWindow();
                    stage.setScene(new Scene((Pane) loader.load()));
                    GameView controller = loader.<GameView>getController();
                    stage.show();
                    controller.init_Multiplayer(currentPlayer, currentLobby);
                } catch (IOException ex) {
                    System.out.println("Error changing scene from Main menu to Settings " + ex.toString());
                }
                System.out.println("Should be new window now");
            }
            //Check if chat message is new
            if (chatBox.getItems().size() > 0) {

            } else {
                client.sendMessage("Welcome to the game " + currentPlayer.getName() + "!");
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
            client.sendMessage(currentPlayer.getName() + ": " + chatMessage.getText().trim());
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
            client.sendMessage("ready: false");
            currentLobby.setPlayerState(0, false);
            System.out.println("readybutton false");
        } else {
            readyButton.getStyleClass().add("ready");
            client.sendMessage("ready: true");
            currentLobby.setPlayerState(0, true);
            System.out.println("readybutton true");
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
        currentLobby.setGameName(textGameName.getText());
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
            controller.init_Multiplayer(currentPlayer, currentLobby);
        } catch (IOException ex) {
            System.out.println("Error changing scene from Lobby to Game " + ex.toString());
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof String) {
            String message = (String) o1;
            if(message.startsWith("Welcome to the game ")){
                if(currentPlayer.getName().equals(message.substring(20, message.length() -1))){
                    System.out.println("this player"); 
                }else{
                    currentLobby.addPlayer(new Human(message.substring(20, message.length() -1), message.substring(19), 20));
                }
            Platform.runLater(() -> {
                chatBox.getItems().add(o1);
                updateLobbyInfo();
            });
            }
            else if(message.startsWith("ready: ")){
                System.out.println(message.substring(7));
                if(message.substring(7).equals("true")){
                    currentLobby.setPlayerState(0, true);
                    if(currentLobby.getAllPlayers().size() == 2)
                    {
                        currentLobby.removePlayer(currentPlayer);
                        currentLobby.addPlayer(new Human("Blue", "Blue", 0));
                        currentLobby.addPlayer(currentPlayer);
                    }
                    ready = true;
                }
                else
                {
                    currentLobby.setPlayerState(0, false);
                    ready = false;
                }
                Platform.runLater(() -> {
                    updateLobbyInfo();
                });
            }
            else{
            Platform.runLater(() -> {
                chatBox.getItems().add(o1);
            });}
        } else if (o1 instanceof Lobby) {
            Platform.runLater(() -> {
                System.out.println("update Lobby");
                currentLobby = (Lobby) o1;
                System.out.println(o1.toString());
                updateLobbyInfo();
            });
        }
    }
}
