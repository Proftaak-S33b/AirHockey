/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.SoundManager;
import java.nio.file.Paths;
import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author HP user
 */
public class AirHockey extends Application {
    
    static MediaPlayer mediaPlayer;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        
        Scene scene = new Scene(mainMenu);
        
        stage.resizableProperty().set(false);
        stage.setTitle("Airhockey - Main Menu");
        
	Random r = new Random();
	int i = r.nextInt(3) + 1;
	SoundManager.play();
	// Media is saved in /Airhockey, not in /src!
	//String mp3 = Paths.get("Airhockey Soundtrack "+ i +".mp3").toUri().toString();
	//Media track = new Media(mp3);	
	//mediaPlayer = new MediaPlayer(track);	
	
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
