/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Joris
 */
public class Server extends Application {

    private rmiStandaloneServer rmi;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridpane = new GridPane();
        TextArea ta = new TextArea();
        ta.setPrefWidth(600);
        ta.setPrefHeight(600);
        ta.wrapTextProperty().set(true);
        gridpane.add(ta, 0, 0);

        Console console = new Console(ta);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);

        primaryStage.setScene(new Scene(gridpane, 800, 600));
        primaryStage.show();

        System.out.println("Starting standalone server..");

        try {
            rmi = new rmiStandaloneServer();
            System.out.println("Running server..");
        } catch (UnknownHostException ex) {
            System.out.println("Failed. Exiting program.");
            System.exit(-1);
        }
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Closing...");
            rmi.exit();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static class Console extends OutputStream {

        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException {
            output.appendText(String.valueOf((char) i));
        }
    }
}
