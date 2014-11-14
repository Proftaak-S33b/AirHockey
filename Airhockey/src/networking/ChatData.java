/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Joris
 */
public class ChatData implements IChatData {

    private final List<Message> messages;

    /**
     * An object for storing Message objects
     */
    public ChatData() {
        messages = new ArrayList<>();
    }

    /**
     * Adds a new Message object to the collection
     *
     * @param message The text message
     * @param sender The IPlayer who sent this message
     * @return True if success, false if failed
     * @throws RemoteException
     */
    @Override
    public boolean add(String message, IPlayer sender) throws RemoteException {
        try {
            messages.add(new Message(message, sender));
            return true;
        } catch (Message.MessageLengthException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Get all Message objects stored by this object
     *
     * @return An unmodifiable List storing all Message objects
     * @throws RemoteException
     */
    @Override
    public List<Message> getAll() throws RemoteException {
        return Collections.unmodifiableList(messages);
    }
}
