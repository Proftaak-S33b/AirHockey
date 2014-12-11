package GUI;

import controllers.SoundManager;
import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author Joris
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
	
	SoundManager.play(SoundManager.SoundEffects.intervention_420);
	
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
