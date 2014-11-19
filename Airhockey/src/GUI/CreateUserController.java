/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.DatabaseManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class CreateUserController implements Initializable {

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO NOPE
    }

    /**
     * When button Create user is pressed
     *
     * @param e
     */
    public void buttonCreateUser(ActionEvent e) {
        if (DatabaseManager.addUser(tfUsername.getText(), tfPassword.getText())) {
            Action response = Dialogs.create()
                    .owner(false ? this : null)
                    .title("Succesvol!")
                    .masthead(false ? "Just Checkin'" : null)
                    .message("Creation succesfull!")
                    .showWarning();
            Node node = (Node) e.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            System.out.println("Creation succesfull");
        } else {
            System.out.println("Creation failed");
                        Action response = Dialogs.create()
                .owner( false ? this : null)                    
                .title("ERROR!")
                .masthead(false ? "Just Checkin'" : null)
                .message( "Creation failed!")
                .showError();
        }
    }

    /**
     * When button cancel is pressed
     *
     * @param e
     */
    public void buttonCancel(ActionEvent e) {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
