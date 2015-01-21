package networking.sockets;

import java.util.ArrayList;

/**
 * Class where all data is saved.
 * Class is static so that it can be used in multiple threads
 * @author Jur
 */
public final class Data {
    //List of the clients
    volatile public static ArrayList<ClientData> clients = new ArrayList<>();
    volatile public static GameData gameData = new GameData();
}
