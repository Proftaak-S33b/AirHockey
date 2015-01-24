/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import networking.commands.Command;
import networking.commands.ReturnCommand;

/**
 *
 * @author Joris
 */
public class Connection {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private LinkedBlockingQueue<Command> queue;

    public Connection(Socket socket, LinkedBlockingQueue<Command> queue) throws IOException {
        this.socket = socket;
        this.queue = queue;
        out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        out.flush();
        in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Command message = (Command) in.readObject();
                    while (message != null) {
                        if (message instanceof ReturnCommand) {
                            ((ReturnCommand) message).setReturnAddress(Connection.this);
                        }
                        queue.put(message);
                        message = (Command) in.readObject();
                    }
                } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }, "ConnectionWatcher");
        t.setDaemon(true);
        t.start();
    }

    /**
     * Writes an object to the stream.
     *
     * @param o the object to send
     */
    public void write(Object o) {
        try {
            out.writeObject(o);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Closes the streams and socket.
     */
    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error closing socket: " + ex.getMessage());
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

    /**
     * Gets the socket this connection uses.
     *
     * @return the socket this connection uses.
     */
    public Socket getSocket() {
        return socket;
    }
}
