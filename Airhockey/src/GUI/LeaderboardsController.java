/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.DatabaseManager;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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

    private ObservableList<Player_Leaderboard> tableData;
    private ObservableList<Player_Leaderboard> dbTableData;

    /**
     * Initializes the controllerclass. Add data to table
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbTableData = DatabaseManager.getTopPlayers();
        tableData = FXCollections.observableArrayList();
        tableData.addAll(dbTableData);
        columnNr.setCellValueFactory(new PropertyValueFactory("Nr"));
        columnPlayerName.setCellValueFactory(new PropertyValueFactory("PlayerName"));
        columnScore.setCellValueFactory(new PropertyValueFactory("Ranking"));
        tablePlayers.setItems(tableData);

    }

    /**
     * Is called when the user presses enter in the search field
     *
     * @param evt
     */
    public void search(ActionEvent evt) {
        tableData.clear();
        tableData = tableData.filtered((Player_Leaderboard t) -> {
            return t.getPlayerName().toLowerCase().matches(".*" + searchText.getText().trim().toLowerCase() + ".*");
        });
    }

    public void clear(ActionEvent evt) {
        tableData.clear();
        tableData.addAll(dbTableData);
    }

    /**
     * Is called when the user clicks the mouse in the tablePlayers n the
     *
     * @param evt
     */
    public void selectTable(Event evt) {

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
