/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import networking.standalone.Connection;

/**
 *
 * @author Joris
 */
public abstract class Receiver {

    protected List<Connection> serverConnections;
    protected LinkedBlockingQueue<Command> queue;
    protected boolean running = true;

    public void Receiver() {
        queue = new LinkedBlockingQueue<>();
        serverConnections = new ArrayList<>();
    }

    /**
     * Sends a text message to all clients connected to this receiver
     *
     * @param text the text message to send
     */
    public void sendMessage(String text) {
        sendToAll(text);
        System.out.println("Received message: " + text);
    }

    /**
     * Adds a connection to the list of connections being managed
     *
     * @param connection
     * @return Returns true if the connection was successfully added. Returns
     * false if the connection was not added or null.
     */
    public boolean addConnection(Connection connection) {
        synchronized (serverConnections) {
            if (connection != null) {
                return serverConnections.add(connection);
            }
            return false;
        }
    }

    /**
     * Removes a connection for the list of connections being managed
     *
     * @param connection the connection to remove
     * @return Returns true if the connection was successfully removed. Returns
     * false if the connection was not removed or not found.
     */
    public boolean removeConnection(Connection connection) {
        synchronized (serverConnections) {
            if (connection != null) {
                return serverConnections.remove(connection);
            }
            return false;
        }
    }

    /**
     * Gets this receiver's queue. Can be used for passing to a Connection so
     * that connection's messages will be processed by this receiver.
     *
     * @return Gets this receiver's LinkedBlockingQueue{@code<Command>}.
     */
    public LinkedBlockingQueue<Command> getQueue() {
        return queue;
    }

    /**
     * Sends an object to all clients in the list
     *
     * @param message the object to send
     */
    private void sendToAll(Object message) {
        for (Connection client : serverConnections) {
            client.write(message);
        }
    }

    /**
     * Ends the messagehandling thread of this receiver
     */
    public void destroy() {
        running = false;
    }
}
