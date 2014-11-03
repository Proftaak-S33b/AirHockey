/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
import javafx.stage.Stage;

/**
 *
 * @author HP user
 */
public class FXMLMainMenuController implements Initializable {

    @FXML
    private ComboBox AIDifficulty;

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
        throw new UnsupportedOperationException("Multiplayer not yet implemented");
    }
}
