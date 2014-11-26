/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.DatabaseManager;
import game.Difficulty;
import game.Human;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.*;

/**
 *
 * @author Joris
 */
public class MainMenuController implements Initializable {

    @FXML
    private ComboBox AIDifficulty;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    private Human currentPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AIDifficulty.getItems().addAll((Object[]) Difficulty.values());
        AIDifficulty.getSelectionModel().select(Difficulty.NORMAL.toString());
        AIDifficulty.setEditable(false);
    }

    public void handleSingleplayer(ActionEvent event) {
        String test = AIDifficulty.getSelectionModel().selectedItemProperty().getValue().toString();
        Difficulty difficulty;
        switch (test) {
            case "Normal":
                difficulty = Difficulty.NORMAL;
                break;
            case "Hard":
                difficulty = Difficulty.HARD;
                break;
            case "Easy":
                difficulty = Difficulty.EASY;
                break;
            default:
                difficulty = Difficulty.EASY;
                break;
        }
        
        try {
           Node node = (Node) event.getSource();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene((Pane) loader.load()));
                GameView controller = loader.<GameView>getController();
                stage.show();
                controller.init_Singleplayer(currentPlayer, difficulty);
            
        } catch (IOException ex) {
            System.out.println("Error changing scene from Main menu to Game " + ex.toString());
        }
    }

    public void handleMultiplayer(ActionEvent event) {
        currentPlayer = new Human("Henk", "test", 400);
        if (DatabaseManager.authenticateUser(tfUsername.getText(), tfPassword.getText())) {
            try {
                Node node = (Node) event.getSource();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LobbyList.fxml"));
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene((Pane) loader.load()));
                LobbyListController controller = loader.<LobbyListController>getController();
                stage.show();
                controller.initData(currentPlayer);
            } catch (IOException ex) {
                System.out.println("Error changing scene from Main menu to LobbyList " + ex.toString());
            }
        } else {
            Action response = Dialogs.create()
                    .owner(false ? this : null)
                    .title("ERROR!")
                    .masthead(false ? "Just Checkin'" : null)
                    .message("Username or password are incorrect!")
                    .showError();
        }
    }

    public void handleSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error changing scene from Main menu to Settings " + ex.toString());
        }
    }

    /**
     * For button create user
     *
     * @param event
     */
    public void handleCreateUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            stage.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error changing scene from Main menu to Settings " + ex.toString());
        }
    }
}
