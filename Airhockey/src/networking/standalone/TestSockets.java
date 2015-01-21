/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Joris
 */
public class TestSockets implements ChangeListener<String> {

    private final Scanner input;
    private ChatSocketClient chat = null;

    public TestSockets() {
        input = new Scanner(System.in);
        try {
            chat = new ChatSocketClient("localhost", 69, this);
        } catch (IOException ex) {
            Logger.getLogger(TestSockets.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread t = new Thread(() -> {
            while (true) {
                chat.send(readString("Type:"));
            }
        });
        t.isDaemon();
        t.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestSockets test = new TestSockets();
    }

    private String readString(String helptext) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestSockets.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(helptext + " ");
        String read = input.nextLine();
        if (read.equals("stop")) {
            System.exit(0);
        }
        return read;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        System.out.println("Message: " + newValue);
    }
}
