package GUI;

import controllers.ChatManager;
import controllers.GameManager;
import controllers.GameManager.GameType;
import fontys.observer.RemotePropertyListener;
import game.AI.AI;
import game.AI.Difficulty;
import game.Human;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import networking.ILobby;
import networking.IPlayer;
import networking.standalone.IClientData;

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class GameView implements Initializable, RemotePropertyListener {

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

    @FXML
    private Label LabelGameEnd;

    @FXML
    private ProgressIndicator piLoading;

    private GraphicsContext gc;

    //Singleplayer
    private IPlayer currentPlayer;
    //Multiplayer
    private ILobby currentLobby;
    private GameManager gamemanager;
    private Difficulty difficulty;
    private ChatManager chat;
    final URL resource = getClass().getResource("nietvanzelf.mp3");
    private ObservableList<IPlayer> players;
    private boolean gameStarted = false;
    private double count = -0.005;
    //Movement commands
    private boolean playerMoveRight;
    private boolean playerMoveLeft;
    private AnimationTimer aniTimer = null;
    private GameType gametype = GameType.SINGLEPLAYER;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = gameCanvas.getGraphicsContext2D();
        chat = new ChatManager();
        listChat.setItems(chat.getMessages());

        //Initialize score table
        columnPlayer.setCellValueFactory(new PropertyValueFactory("Name"));
        columnScore.setCellValueFactory(new PropertyValueFactory("Ranking"));
    }

    /**
     * Has to be called when starting singleplayer mode
     *
     * @param player The player that plays this game
     * @param difficulty The difficulty of the AI
     */
    public void init_Singleplayer(Human player, Difficulty difficulty) {
        if (!gameStarted) {
            currentPlayer = player;
            players = FXCollections.observableArrayList();
            players.add(currentPlayer);
            players.add(new AI("Blue", 20));
            players.add(new AI("Green", 20));
            tableScore.setItems((ObservableList) players);
            this.difficulty = difficulty;
            gamemanager = new GameManager(gc, players, difficulty, GameType.SINGLEPLAYER, this, null, null);

            piLoading.setVisible(true);
            Timer t = new Timer("CountdownTimer", true);

            t.schedule(new TimerTask() {

                @Override
                public void run() {
                    count += 0.005;

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            loadProgress();
                        }
                    });
                    if (count >= 5.50) {
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                progressVisible();
                                gamemanager.start();
                                gameStarted = true;
                            }
                        });
                        t.cancel();
                    }
                }
            }, 0, 5);
        }
        aniTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                boolean gameBusy = gamemanager.draw();
                //Refresh scoretable
                ObservableList<IPlayer> data = FXCollections.observableArrayList();
                for (IPlayer player : players) {
                    data.add(player);
                }
                players.removeAll(players);
                players.addAll(data);

                if (!gameBusy) {
                    this.stop();
                } else {
                    player_Move();
                }
            }
        };
        aniTimer.start();

    }

    /**
     * Set the progress in the progressindicator
     */
    public void loadProgress() {
        piLoading.setProgress(count / 5);
    }

    /**
     * set the visiblty of the progressindicator false
     */
    public void progressVisible() {
        piLoading.setVisible(false);
    }

    /**
     * Has to be called when starting multiplayer game
     *
     * @param player The player that is currently playing
     * @param lobby The lobby object that represents this game
     * @param clientData
     */
    public void init_Multiplayer(Human player, ILobby lobby, IClientData clientData) {
        if (!gameStarted) {
            currentPlayer = player;
            currentLobby = lobby;
            try {
                UnicastRemoteObject.exportObject(this, 9999);
                lobby.addListener(this, "newMessage");
            } catch (RemoteException ex) {
                System.out.println("RemoteException: " + ex.getMessage());
            }
            List<IPlayer> playersInLobby;
            try {
                playersInLobby = lobby.getAllPlayers();
            } catch (RemoteException ex) {
                System.out.println("RemoteException: " + ex.getMessage());
                playersInLobby = null;
            }
            //Get which gamemode has to be set
            if (playersInLobby.get(0).getName().equals(currentPlayer.getName())) {
                gametype = GameType.MULTIPLAYER_RED;
                System.out.println("U are player red");
            } else if (playersInLobby.get(1).getName().equals(currentPlayer.getName())) {
                gametype = GameType.MULTIPLAYER_BLUE;
                System.out.println("U are player blue");
                gameCanvas.getTransforms().add(new Rotate(0,0,300));
            } else {
                gametype = GameType.MULTIPLAYER_GREEN;
                System.out.println("U are player green");
                gameCanvas.getTransforms().add(new Rotate(0,0,60));
            }
            //Add all players to gamedata
            players = FXCollections.observableArrayList();
            for (IPlayer tempPlayer : playersInLobby) {
                players.add(tempPlayer);
            }
            //Set dificulty to prevent errors
            difficulty = Difficulty.NORMAL;
            gamemanager = new GameManager(gc, players, difficulty, gametype, this, currentLobby, clientData);
            Timer t = new Timer("CountdownTimer", true);

            t.schedule(new TimerTask() {

                @Override
                public void run() {
                    count += 0.005;

                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            loadProgress();
                        }
                    });
                    if (count >= 5.50) {
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                progressVisible();
                                gamemanager.start();
                                gameStarted = true;
                            }
                        });
                        t.cancel();
                    }
                }
            }, 0, 5);
            aniTimer = new AnimationTimer() {

                @Override
                public void handle(long now) {
                    boolean gameBusy = gamemanager.draw();
                    //Refresh scoretable
                    ObservableList<IPlayer> data = FXCollections.observableArrayList();
                    for (IPlayer player : players) {
                        data.add(player);
                    }
                    players.removeAll(players);
                    players.addAll(data);

                    if (!gameBusy) {
                        this.stop();
                    } else {
                        player_Move();
                    }
                }
            };
            aniTimer.start();
        }
    }

    /**
     * Method for handling send chat button click
     *
     * @param event
     */
    public void setTekst(ActionEvent event) {
        if (!textChat.getText().equals("")) {
            if (gametype == GameType.SINGLEPLAYER) {
                listChat.getItems().add(players.get(0).getName() + ": " + textChat.getText());
                listChat.getItems().add("AI: " + new AI("", 20).chat());
                textChat.clear();
            } else {
                try {
                    currentLobby.setLastChatMessage(currentPlayer.getName() + ": " + textChat.getText());
                } catch (Exception ex) {
                    System.out.println("Set chat message failed");
                    System.out.println(ex.getMessage());
                }
                textChat.clear();
            }
        }
    }

    /**
     * Method for handling exit button click
     *
     * @param event
     */
    public void exit(ActionEvent event) {
        gamemanager.destroy();
        aniTimer.stop();
        try {
            Node node = (Node) event.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error changing scene from LobbyList to MainMenu " + ex.toString());
        }
    }

    /**
     * Checks which buttons are pressed and moves the player
     */
    private void player_Move() {
        gamemanager.player_Move(playerMoveRight, playerMoveLeft, gametype);
    }

    /**
     * Checks if a key is pressed and adjusts the player movement.
     *
     * @param event the keyevent that happened
     */
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                if (playerMoveRight) {
                    playerMoveRight = false;
                }
                playerMoveLeft = true;
                break;
            case RIGHT:
                if (playerMoveLeft) {
                    playerMoveLeft = false;
                }
                playerMoveRight = true;
                ;
                break;
            case M:
                //pause/mute background music.
                if (AirHockey.mediaPlayer.getStatus().toString().equals("PLAYING")) {
                    AirHockey.mediaPlayer.pause();
                } else {
                    AirHockey.mediaPlayer.play();
                }
                break;
        }
    }

    /**
     * Checks if a key is released and adjusts the player movement.
     *
     * @param event the keyevent that happened
     */
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                if (playerMoveLeft) {
                    playerMoveLeft = false;
                }
                break;
            case RIGHT:
                if (playerMoveRight) {
                    playerMoveRight = false;
                }
                break;
        }
    }

    /**
     * Sets the label for when the game has ended
     */
    public void SetEndLabel() {
        LabelGameEnd.setText("Game Ended!");
        LabelGameEnd.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    chat.addMessage(currentLobby.getLastChatMessage());
                } catch (RemoteException ex) {
                    System.out.println("RemoteException for chat: " + ex.getMessage());
                }
            }
        });
    }
}
