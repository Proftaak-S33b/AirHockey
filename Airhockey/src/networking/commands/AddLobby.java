package networking.commands;

import networking.IPlayer;
import networking.standalone.Connection;

/**
 * Command for adding a lobby to the server.
 *
 * @author Etienne
 */
public class AddLobby implements ServerCommand, ReturnCommand {

    private String name;
    private IPlayer host;
    private Connection connection;
    private ServerReceiver receiver;

    /**
     * Gives the command the needed parameters.
     *
     * @param name
     * @param host
     */
    public AddLobby(String name, IPlayer host) {
        this.name = name;
        this.host = host;
    }

    /**
     * Execute addLobby
     */
    @Override
    public void Execute() {
        receiver.addLobby(name, host, connection);
    }

    /**
     * set the receiver
     *
     * @param receiver
     */
    @Override
    public void SetReceiver(ServerReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void setReturnAddress(Connection conn) {
        connection = conn;
    }
}
