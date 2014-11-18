/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class LeaderboardsController implements Initializable {

    @FXML
    private TableView tablePlayers;

    @FXML
    private TableColumn columnNr;

    @FXML
    private TableColumn columnName;

    @FXML
    private TableColumn columnScore;

    @FXML
    private TableColumn columnNrGames;

    @FXML
    private TextField searchText;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Database something something
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
     *
     * @param evt
     */
    public void selectTable(ActionEvent evt) {

    }

    /**
     * Is called when the user presses the {@code "< Back"} button
     *
     * @param evt
     */
    public void back(ActionEvent evt) {

    }
}
