/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import game.IPlayer;
import java.io.Serializable;

/**
 *
 * @author Joris
 */
public class Message implements Serializable{

    public class MessageLengthException extends Exception {

        public MessageLengthException() {

        }
    }

    private final String text;
    private final IPlayer sender;

    /**
     * A new message with specified text and sender
     *
     * @param text The text of this message
     * @param sender
     * @throws shared.Message.MessageLengthException
     */
    public Message(String text, IPlayer sender) throws MessageLengthException {
        if (text.length() <= 144) {
            this.text = text;
        } else {
            throw new MessageLengthException();
        }
        this.sender = sender;
    }

    /**
     * Gets the text of this message
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the name of the sender
     *
     * @return
     */
    public String getSenderName() {
        return sender.getName();
    }
}
