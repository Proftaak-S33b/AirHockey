/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.DatabaseManager;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import networking.Player_Leaderboard;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class LeaderboardsController implements Initializable {

    @FXML
    private TableView tablePlayers;

    @FXML
    private TableColumn columnPlayerName;

    @FXML
    private TableColumn columnScore;
    
    @FXML
    private TableColumn columnNr;

    @FXML
    private TextField searchText;

    /**
     * Initializes the controllerclass.
     * Add data to table
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Player_Leaderboard> players = DatabaseManager.getTopPlayers();
        columnNr.setCellValueFactory(new PropertyValueFactory("Nr"));
        columnPlayerName.setCellValueFactory(new PropertyValueFactory("PlayerName"));
        columnScore.setCellValueFactory(new PropertyValueFactory("Ranking"));
        tablePlayers.setItems(players);
        
        
    }

    /**
     * Is called when the user presses enter in the search field
     *
     * @param evt
     */
    public void search(ActionEvent evt) {

    }

    /**
     * Is called when the user clicks the mouse in the tablePlayers
     *n the
     * @param evt
     */
    public void selectTable(ActionEvent evt) {

    }

    /**
     * Is called when the user presses the {@code "< Back"} button
     *
     * @param e
     */
    public void back(ActionEvent e) {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
