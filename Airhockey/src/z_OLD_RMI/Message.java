/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z_OLD_RMI;

import java.io.Serializable;

/**
 *
 * @author Joris
 */
public class Message implements Serializable {

    public class MessageLengthException extends Exception {

        public MessageLengthException() {

        }
    }

    private final String text;

    /**
     * A new message with specified text and sender
     *
     * @param text The text of this message
     * @param sender
     * @throws networking.Message.MessageLengthException
     */
    public Message(String text) throws MessageLengthException {
        if (text.length() <= 144) {
            this.text = text;
        } else {
            throw new MessageLengthException();
        }
    }

    /**
     * Gets the text of this message
     *
     * @return
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
