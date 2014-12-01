package GUI;

//<editor-fold defaultstate="collapsed" desc="imports">
import controllers.GameManager;
import game.AI;
import game.Difficulty;
import game.Human;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import networking.IPlayer;
import networking.Lobby;

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class GameView implements Initializable {

  
    private enum GameType {

        SINGLEPLAYER,
        MULTIPLAYER
    }

    @FXML
    private Button buttonChat;

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

    private GraphicsContext gc;

    //Singleplayer
    private IPlayer currentPlayer;
    //Multiplayer
    private Lobby currentLobby;
    private GameType gametype;
    private GameManager gamemanager;
    private Difficulty difficulty;

    private ObservableList<IPlayer> players;

    // Scales the physics to the drawing.


    //Movement commands
    private boolean playerMoveRight;
    private boolean playerMoveLeft;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = gameCanvas.getGraphicsContext2D();
        
        //Initialize score table
        columnPlayer.setCellValueFactory(new PropertyValueFactory("Name"));
        columnScore.setCellValueFactory(new PropertyValueFactory("Ranking"));
    }

    public void init_Singleplayer(Human player, Difficulty difficulty) {
        currentPlayer = player;
        players = FXCollections.observableArrayList();
        players.add(currentPlayer);
        players.add(new AI("Com1", 20));
        players.add(new AI("Com2", 20));
        tableScore.setItems((ObservableList) players);
        gametype = GameType.SINGLEPLAYER;
        this.difficulty = difficulty;
        gamemanager = new GameManager(gc , players, difficulty);
        
                new AnimationTimer() {

            @Override
            public void handle(long now) {
                gamemanager.draw();
                player_Move();
                //Refresh scoretable
                ObservableList<IPlayer> data = FXCollections.observableArrayList();
                for(IPlayer player : players){
                    data.add(player);
                }
                players.removeAll(players);
                players.addAll(data);
            }
        }.start();

        gamemanager.start();
    }

    public void init_Multiplayer(Human player, Lobby lobby) {
        currentPlayer = player;
        currentLobby = lobby;
        gametype = GameType.MULTIPLAYER;
    }

    /**
     * Method for handling send chat button click
     *
     * @param event
     */
    public void setTekst(ActionEvent event) {
        if (!textChat.getText().equals("")) {
            listChat.getItems().add(textChat.getText());
            textChat.clear();
        }
    }

    public void exit(ActionEvent event) {
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
        gamemanager.player_Move(playerMoveRight, playerMoveLeft);
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
                playerMoveRight = true;;
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
}
