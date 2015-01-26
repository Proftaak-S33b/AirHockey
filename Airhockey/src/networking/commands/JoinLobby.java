package networking.commands;

import networking.IPlayer;
import networking.standalone.Connection;

/**
 *
 * @author Etienne
 */
public class JoinLobby implements ServerCommand, ReturnCommand {
    
    private int id;
    private IPlayer player;
    private Connection connection;
    private ServerReceiver receiver;

    /**
     * Joins the lobby of the given player.
     * @param id
     * @param player 
     */
    public JoinLobby(int id, IPlayer player) {
	this.id = id;
	this.player = player;
    }        
    
    /**
     * execute joinlobby
     */
    @Override
    public void execute() {
        receiver.joinLobby(id, player, connection);
    }        

    /**
     * set the receiver
     * @param receiver 
     */
    @Override
    public void setReceiver(ServerReceiver receiver) {
	this.receiver = receiver;
    }

    @Override
    public void setReturnAddress(Connection conn) {
        this.connection = conn;
    }
}
