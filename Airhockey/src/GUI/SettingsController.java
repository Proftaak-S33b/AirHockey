/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.PropertiesController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Load properties from file and write to fields
        if(PropertiesController.loadProperties())
        {
            //Fill rmi fields
            rmiIPAddress.setText(PropertiesController.getSettings().getProperty("rmiurl"));
            rmiPort.setText(PropertiesController.getSettings().getProperty("rmiport"));
            rmiRegistry.setText(PropertiesController.getSettings().getProperty("rmiregistry"));
            
            //Fill database fields
            dbIPAddress.setText(PropertiesController.getSettings().getProperty("dburl"));
            dbPort.setText(PropertiesController.getSettings().getProperty("dbport"));
            dbUsername.setText(PropertiesController.getSettings().getProperty("dbusername"));
            dbPassword.setText(PropertiesController.getSettings().getProperty("dbpassword"));
        }
    }

    public void backButton(ActionEvent evt) {
        //Write all properties back to properties.
        if(!PropertiesController.writeProperties(
                rmiIPAddress.getText(), 
                rmiPort.getText(), 
                rmiRegistry.getText(), 
                dbIPAddress.getText(),
                dbPort.getText(), 
                dbUsername.getText(),
                dbPassword.getText())){
            System.out.println("Properties couldn't be saved!");
        }
        
        Node node = (Node) evt.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
