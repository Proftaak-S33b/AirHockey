package client_hosted_chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example of a player hosted system, except now for chat.
 *
 * @author Jur
 */
public class Client_Hosted_Chat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Listen for user input
        Scanner user_input = new Scanner(System.in);
        //Ask if user wants to be a new host, or connect to an excisting host
        System.out.println("SYSTEM: Enter host ip adress");
        System.out.println("SYSTEM: Enter new to be the new host");
        String input = user_input.next();
        if (input.equals("new")) {
            System.out.println("SYSTEM: Starting new host...");
            Thread t = new Thread(new Host());
            t.start();
            Socket socket;
            try {
                socket = new Socket("127.0.0.1", 4444);
                t = new Thread(new Client(true, socket));
                t.start();
            } catch (IOException ex) {
                System.out.println("SYSTEM: Connection to own pc failed...!");
            }
        } else {
            System.out.println("SYSTEM: Connecting to host...");
            Socket socket;
            try {
                socket = new Socket(input, 4444);
                Thread t = new Thread(new Client(false, socket));
                t.start();
                System.out.println("SYSTEM: Connected!");
            } catch (IOException ex) {
                System.out.println("Connection to host failed!");
            }
        }
    }

}
