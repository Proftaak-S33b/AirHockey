package networking.commands;

/**
 * Command for sending a message to the chat.
 *
 * @author Etienne
 */
public class SendMessage implements ServerCommand, LobbyCommand, GameCommand {

    private String message;
    private ServerReceiver sReceiver;
    private LobbyReceiver lReceiver;
    private GameReceiver gReceiver;

    /**
     * Instantiates a new SendMessage command
     *
     * @param message
     */
    public SendMessage(String message) {
        this.message = message;
    }

    /**
     * Execute sendMessage
     */
    @Override
    public void execute() {
        if (sReceiver != null) {
            sReceiver.sendMessage(message);
        } else if (lReceiver != null) {
            lReceiver.sendMessage(message);
        } else if (gReceiver != null) {
            gReceiver.addMessage(message);
        }
    }

    /**
     * set the receiver
     *
     * @param receiver
     */
    @Override
    public void setReceiver(ServerReceiver receiver) {
        this.sReceiver = receiver;
    }

    /**
     * set the receiver
     *
     * @param receiver
     */
    @Override
    public void setReceiver(LobbyReceiver receiver) {
        this.lReceiver = receiver;
    }

    /**
     * set the receiver
     *
     * @param receiver
     */
    @Override
    public void setReceiver(GameReceiver receiver) {
        this.gReceiver = receiver;
    }
}
