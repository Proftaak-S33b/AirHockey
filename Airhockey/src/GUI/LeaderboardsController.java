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
    private final ObservableList<Player_Leaderboard> dbTableData = DatabaseManager.getTopPlayers();

    /**
     * Initializes the controllerclass. Add data to table
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableData = FXCollections.observableArrayList();
        tableData.addAll(dbTableData);
        columnNr.setCellValueFactory(new PropertyValueFactory("Nr"));
        columnPlayerName.setCellValueFactory(new PropertyValueFactory("PlayerName"));
        columnScore.setCellValueFactory(new PropertyValueFactory("Ranking"));
        tablePlayers.setItems(tableData);

    }
    
   /**
    * Improvement of searchfield by searchen when the string in the
    * searchfield has changed
    * @param e 
    */
   public void liveSearch(Event e) {
       //Check if searchfield has become empty. Ifso display all players.
       if(searchText.getText().trim().equals("")){
          resetTable();
       }
       //Else check for playernames that contain the searchstring or
       //the number matches the number of the player.
       else
       {
           tableData.clear();
           for (Player_Leaderboard p : dbTableData) {
               if (p.getPlayerName().toLowerCase().contains(searchText.getText().trim().toLowerCase()) 
                       || ("" + p.getNr()).equals(searchText.getText().trim().toLowerCase())) {
                   tableData.add(p);
                   
               }
           }
       }
    }
    /**
     * Reset the table to display all the players
     */
    public void resetTable() {
        tableData.clear();
        searchText.clear();
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
