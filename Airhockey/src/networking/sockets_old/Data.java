package networking.sockets_old;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class where all data is saved.
 * Class is static so that it can be used in multiple threads
 * @author Jur
 */
public final class Data {
    //Last sent message
    volatile public static String LastMessage = "";
    //List of the clients
    volatile public static ArrayList<ClientData> clients = new ArrayList<>();
    //Unique client number
    volatile public static int clientNr = 0;
}
