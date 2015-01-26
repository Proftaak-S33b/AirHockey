/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.LinkedBlockingQueue;
import networking.commands.Command;
import networking.commands.ReturnCommand;

/**
 *
 * @author Joris
 */
public class ConnectionWatcher implements Runnable {

    private ObjectInputStream in;
    private LinkedBlockingQueue<Command> queue;
    private Connection connection;

    /**
     * 
     * @param queue
     * @param in
     * @param connection 
     */
    public ConnectionWatcher(LinkedBlockingQueue<Command> queue, ObjectInputStream in, Connection connection) {
        this.queue = queue;
        this.in = in;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            Command message = (Command) in.readObject();
            while (message != null) {
                if (message instanceof ReturnCommand) {
                    ((ReturnCommand) message).setReturnAddress(connection);
                }
                queue.put(message);
                message = (Command) in.readObject();
            }
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Makes this connection put incoming commands into the new queue so they
     * can be processed.
     *
     * @param queue the new queue this connection will put new commands into
     */
    public void setQueue(LinkedBlockingQueue<Command> queue) {
        this.queue = queue;
    }
}
