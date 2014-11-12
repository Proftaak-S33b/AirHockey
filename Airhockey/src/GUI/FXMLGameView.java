package GUI;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Joris Douven
 */
public class FXMLGameView implements Initializable, EventHandler<KeyEvent> {

    @FXML
    private Button buttonChat;

    @FXML
    private TextField textChat;

    @FXML
    private ListView listChat;

    @FXML
    private TableView tableScore;

    @FXML
    private TableColumn columnPlayer;

    @FXML
    private TableColumn columnScore;

    @FXML
    private Canvas gameCanvas;

    GraphicsContext gc;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = gameCanvas.getGraphicsContext2D();

    }

    /**
     * Method for handling send chat button click
     *
     * @param event
     */
    public void SetTekst(ActionEvent event) {
        if(!textChat.getText().equals(""))
        {
            listChat.getItems().add(textChat.getText());
            textChat.clear();
        }
    }

    @Override
    public void handle(KeyEvent event) {
        System.out.println(event.getCharacter());
    }
}
