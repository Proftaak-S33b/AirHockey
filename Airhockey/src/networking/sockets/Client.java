/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import networking.IPlayer;
import networking.commands.*;
import networking.standalone.Lobby;

/**
 *
 * @author Joris
 */
public class Client {

    private ConnectionToServer connection;
    private LinkedBlockingDeque<Object> messages;
    private Socket socket;
    private ReadOnlyStringWrapper observableMessage;
    private ObservableList<Lobby> lobbyList;
    private ReentrantLock lock;

    /**
     *
     * @param IPAddress
     * @param port
     * @param changeListener
     * @throws IOException
     */
    public Client(String IPAddress, int port, ChangeListener<String> changeListener) throws IOException {
        socket = new Socket(IPAddress, port);
        messages = new LinkedBlockingDeque<>();
        connection = new ConnectionToServer(socket);
        observableMessage = new ReadOnlyStringWrapper();
        observableMessage.addListener(changeListener);
        lock = new ReentrantLock();

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object message = messages.take();
                        if (message instanceof String) {
                            System.out.println("Received string: " + message);
                            observableMessage.set((String) message);
                        } else if (message instanceof List) {
                            System.out.println("Changing lobbyList");
                            lobbyList.clear();
                            lobbyList.addAll((List<Lobby>) message);
                            lock.unlock();
                        } else {
                            System.out.println("Unknown class type received");
                        }
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException: " + e.getMessage());
                    }
                }
            }
        };
        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    /**
     * A class which contains a method to write to the socket and the class will
     * handle incoming messages by putting them into a queue.
     */
    private class ConnectionToServer {

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            Thread read = new Thread() {
                @Override
                public void run() {
                    try {
                        Object message = in.readObject();
                        while (message != null) {
                            messages.put(message);
                            message = in.readObject();
                        }
                    } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            };
            read.setDaemon(true);
            read.start();
        }

        private void write(Command message) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     *
     * @param message
     */
    private void send(Command message) {
        connection.write(message);
    }

    /**
     * Tells the server to create a new lobby with specified name and player as
     * 'host'.
     *
     * @param name The name of the lobby to create
     * @param me The player that will be the host
     */
    public void addLobby(String name, IPlayer me) {
        send(new AddLobby(name, me));
    }

    /**
     * Tells the server to change the name of the specified lobby
     *
     * @param lobby the lobby of which the name should be changed
     * @param name the new name
     */
    public void changeLobbyName(Lobby lobby, String name) {
        send(new ChangeLobbyname(lobby.getID(), name));
    }

    /**
     * Tells the server to transfer this player to the specified lobby.
     *
     * @param lobby the lobby to join
     * @param me the player to join the lobby
     */
    public void joinLobby(Lobby lobby, IPlayer me) {
        send(new JoinLobby(lobby.getID(), me));
    }

    /**
     * Sends a text message to the server.
     *
     * @param message the text message
     * @param me the sender of the message
     */
    public void sendMessage(String message, IPlayer me) {
        send(new SendMessage(me.getName() + ": " + message));
    }

    /**
     * Gets all the lobbies from the server. Will block until the lobbies have
     * been received.
     *
     * @return a list containing all lobbies
     */
    public List<Lobby> getLobbies() {
        send(new GetLobbies());
        lock.lock();
        return lobbyList;
    }
}
