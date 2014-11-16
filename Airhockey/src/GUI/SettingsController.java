/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.PropertiesController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joris
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField rmiIPAddress;

    @FXML
    private TextField rmiPort;

    @FXML
    private TextField rmiRegistry;

    @FXML
    private TextField dbIPAddress;

    @FXML
    private TextField dbPort;

    @FXML
    private TextField dbUsername;

    @FXML
    private TextField dbPassword;

    private PropertiesController settings;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Something with a properties file ->
        settings = new PropertiesController(null);
        //settings.loadProperties();
    }

    public void backButton(ActionEvent evt) {
        Stage stage = (Stage) rmiIPAddress.getScene().getWindow();
        stage.close();
    }
}
