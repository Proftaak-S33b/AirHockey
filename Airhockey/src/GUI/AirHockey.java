/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author HP user
 */
public class AirHockey extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        
        Scene scene = new Scene(mainMenu);
        
        stage.resizableProperty().set(false);
        stage.setTitle("Airhockey - Main Menu");
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
