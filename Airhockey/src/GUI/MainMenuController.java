/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.DatabaseManager;
import game.Human;
import z_OLD_game.Difficulty;
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
import org.controlsfx.*;

/**
 *
 * @author HP user
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
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("GameView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Airhockey - In singleplayer game");
            stage.setScene(scene);
            stage.show();
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
        }
        else{
            Action response = Dialogs.create()
                .owner( false ? this : null)                    
                .title("ERROR!")
                .masthead(false ? "Just Checkin'" : null)
                .message( "Username or password are incorrect!")
                .showError();}
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
}
