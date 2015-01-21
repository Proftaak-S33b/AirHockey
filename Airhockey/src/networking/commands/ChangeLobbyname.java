package networking.commands;

/**
 *
 * @author Etienne
 */
public class ChangeLobbyname implements ServerCommand {

    private int lobby_id;
    private String newlobbyname;

    /**
     * Changes the lobbyname to the one specified.
     *
     * @param lobby_id
     * @param newlobbyname
     */
    public ChangeLobbyname(int lobby_id, String newlobbyname) {
        this.lobby_id = lobby_id;
        this.newlobbyname = newlobbyname;
    }

    public ServerReceiver receiver;

    @Override
    public void Execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetReceiver(ServerReceiver receiver) {
        this.receiver = receiver;
    }
}
