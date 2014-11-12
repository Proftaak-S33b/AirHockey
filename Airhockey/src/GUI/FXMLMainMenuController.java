/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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

/**
 *
 * @author HP user
 */
public class FXMLMainMenuController implements Initializable {

    @FXML
    private ComboBox AIDifficulty;

    private Human currentPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AIDifficulty.getItems().addAll((Object[]) Difficulty.values());
        AIDifficulty.getSelectionModel().select(Difficulty.NORMAL.toString());
        AIDifficulty.setEditable(false);
    }

    @FXML
    private void handleSingleplayer(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("FXMLGameView.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Airhockey - In singleplayer game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error changing scene from Main menu to Game " + ex.toString());
        }
    }

    @FXML
    private void handleMultiplayer(ActionEvent event) {
        //http://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
        currentPlayer = new Human("Henk", "test", 400);
        try {
            Node node = (Node) event.getSource();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLobbyList.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene((Pane) loader.load()));
            FXMLLobbyListController controller = loader.<FXMLLobbyListController>getController();
            controller.initData(currentPlayer);
            stage.setTitle("Lobbies - Hello, " + currentPlayer.getName());
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error changing scene from Main menu to LobbyList " + ex.toString());
        }
    }
}
