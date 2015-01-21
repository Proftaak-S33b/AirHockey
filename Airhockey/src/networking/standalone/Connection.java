/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.*;
import java.net.Socket;
import networking.commands.Command;

/**
 *
 * @author Joris
 */
public class Connection {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    /**
     * If available, reads an object from the stream. This method will not
     * block, although if large objects are being sent it may take a little
     * while
     *
     * @return The read ICommand, or null if the stream was empty
     */
    public Command read() {
        try {
            Command message;
            if (in.available() > 0) {
                if ((message = (Command) in.readObject()) != null) {
                    return message;
                }
            }
        } catch (IOException ex) {
            System.out.println("CTC Error:" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Writes an object to the stream
     *
     * @param o the ICommand to send
     */
    public void write(Command o) {
        try {
            out.writeObject(o);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
