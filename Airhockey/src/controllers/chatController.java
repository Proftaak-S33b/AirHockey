/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.IPlayer;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Message;

/**
 * Controller for managing the chat in games
 *
 * @author Joris
 */
public class ChatController implements Serializable{

    private transient ObservableList<Message> messagesObservable;
    private final ArrayList<Message> messages;

    /**
     * Instantiates a new chatController
     */
    public ChatController() {
        messages = new ArrayList<>();
        messagesObservable = FXCollections.observableArrayList(messages);
    }

    /**
     * Adds a message to this controller
     * @param text
     * @param sender
     * @return 
     */
    public boolean addMessage(String text, IPlayer sender) {
        try {
            messagesObservable.add(new Message(text, sender));
        } catch (Message.MessageLengthException ex) {
            return false;
        }
        return true;
    }

    /**
     * Gets and unmodifiable ObservableList with all messages registered by this chatController
     * @return 
     */
    public ObservableList<Message> getMessages() {
        return FXCollections.unmodifiableObservableList(messagesObservable);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        messagesObservable = FXCollections.observableArrayList(messages);
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
        messages.clear();
        for (Message l : messagesObservable) {
            messages.add(l);
        }
    }
}
