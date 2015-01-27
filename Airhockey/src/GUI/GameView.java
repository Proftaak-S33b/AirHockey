package GUI;

import controllers.GameManager;
import controllers.GameManager.GameType;
import game.AI.AI;
import game.AI.Difficulty;
import game.Human;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import networking.IPlayer;
import networking.standalone.Lobby;

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class GameView implements Initializable {

    @FXML
    private TextField textChat;

    @FXML
    public ListView listChat;

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
    public IPlayer currentPlayer;
    //Multiplayer
    private Lobby currentLobby;
    private GameManager gamemanager;
    private Difficulty difficulty;
    final URL resource = getClass().getResource("nietvanzelf.mp3");
    private ObservableList<PlayerInGame> players;
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

        //Initialize score table
        columnPlayer.setCellValueFactory(new PropertyValueFactory("Name"));
        columnScore.setCellValueFactory(new PropertyValueFactory("Score"));
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
            players.add(new PlayerInGame(currentPlayer.getName()));
            players.add(new PlayerInGame("Blue"));
            players.add(new PlayerInGame("Green"));
            tableScore.setItems((ObservableList) players);
            this.difficulty = difficulty;
            gamemanager = new GameManager(gc, players, difficulty, GameType.SINGLEPLAYER, this, null);

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
                ObservableList<PlayerInGame> data = FXCollections.observableArrayList();
                for (PlayerInGame player : players) {
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
    public void init_Multiplayer(Human player, Lobby lobby) {
        if (!gameStarted) {
            currentPlayer = player;
            currentLobby = lobby;
            List<IPlayer> playersInLobby;
            playersInLobby = lobby.getAllPlayers();
            //Get which gamemode has to be set
            if (playersInLobby.get(0).getName().equals(currentPlayer.getName())) {
                gametype = GameType.MULTIPLAYER_RED;
                System.out.println("U are player red");
            } else if (playersInLobby.get(1).getName().equals(currentPlayer.getName())) {
                gametype = GameType.MULTIPLAYER_BLUE;
                System.out.println("U are player blue");
                gameCanvas.getTransforms().add(new Rotate(120, 250, 180));
            } else if (playersInLobby.get(2).getName().equals(currentPlayer.getName())) {
                gametype = GameType.MULTIPLAYER_GREEN;
                System.out.println("U are player green");
                gameCanvas.getTransforms().add(new Rotate(240, 250, 180));
            } else {
                gametype = GameType.SPECTATING;
                System.out.println("U are player green");
            }
            //Add all players to gamedata
            players = FXCollections.observableArrayList();
            for (IPlayer tempPlayer : playersInLobby) {
                players.add(new PlayerInGame(tempPlayer.getName()));
            }
            tableScore.setItems((ObservableList) players);
            //Set difficulty to prevent errors
            difficulty = Difficulty.NORMAL;
            gamemanager = new GameManager(gc, players, difficulty, gametype, this, currentLobby);
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

//                    //Refresh scoretable
//                    ObservableList<PlayerInGame> data = FXCollections.observableArrayList();
//                    for (PlayerInGame player : players) {
//                        data.add(player);
//                    }
//                    players.removeAll(players);
//                    players.addAll(data);
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
     * Method for handling "send" chatbutton click.
     *
     * @param event
     */
    public void setTekst(ActionEvent event) {
        boolean stringnotempty = !textChat.getText().equals("");
        if (gametype != GameType.SINGLEPLAYER & stringnotempty) {
            // Send it to everyone else.
            gamemanager.sendMessage(textChat.getText());
            // Add it to the box locally.
            listChat.getItems().add(players.get(0).getName() + ": " + textChat.getText());
        } else if (stringnotempty) {
            listChat.getItems().add(players.get(0).getName() + ": " + textChat.getText());
            listChat.getItems().add("AI: " + new AI("", 20).chat());
            textChat.clear();
        }
    }

    /**
     * Method for handling incoming messages from the controller.
     *
     * @param message
     */
    public void setTekst(String message) {
        listChat.getItems().add(message);
    }

    /**
     * Method for handling exit button click
     *
     * @param event
     */
    public void exit(ActionEvent event) {
        try {
            gamemanager.destroy();
            aniTimer.stop();
            Node node = (Node) event.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.show();
        } catch (IOException | NullPointerException ex) {
            System.out.println("Error changing scene from LobbyList to MainMenu " + ex.toString());
        }
    }

    /**
     * Checks which buttons are pressed and moves the player
     */
    private void player_Move() {
        if (gametype == GameType.SINGLEPLAYER) {
            gamemanager.player_Move(playerMoveRight, playerMoveLeft, gametype);
        } else {
            if (playerMoveLeft == true) {
                gamemanager.player_Move(gametype, "Left");
            } else if (playerMoveRight == true) {
                gamemanager.player_Move(gametype, "Right");
            }
        }
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
}
