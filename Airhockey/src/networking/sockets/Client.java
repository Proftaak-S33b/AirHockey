package networking.sockets;

import controllers.GameManager.GameType;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.IPlayer;
import networking.commands.*;
import networking.standalone.Lobby;

/**
 *
 * @author Joris
 */
public class Client extends Observable {

    private ConnectionToServer connection;
    private LinkedBlockingDeque<Object> messages;
    private Socket socket;
    private String message;
    private Lobby lobby;
    private ObservableList<Lobby> lobbyList;

    /**
     *
     * @param IPAddress
     * @param port
     * @throws IOException
     */
    public Client(String IPAddress, int port) throws IOException {
        socket = new Socket(IPAddress, port);
        messages = new LinkedBlockingDeque<>();
        connection = new ConnectionToServer(socket);
        lobbyList = FXCollections.observableArrayList();

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object message = messages.take();
                        if (message instanceof String) {
                            System.out.println("Received string: " + message);
                            Client.this.message = (String) message;
                            Client.super.setChanged();
                            Client.super.notifyObservers(Client.this.message);
                        } else if (message instanceof List) {
                            synchronized (lobbyList) {
                                System.out.println("Changing lobbyList");
                                lobbyList.clear();
                                lobbyList.addAll((List<Lobby>) message);
                                lobbyList.notify();
                            }
                        } else if (message instanceof Lobby) {
                            System.out.println("Received lobby: " + message.toString());
                            System.out.println("Amount of players: " + ((Lobby) message).getPlayersAmount());
                            Client.this.lobby = (Lobby) message;
                            Client.super.setChanged();
                            Client.super.notifyObservers(message);
                            //This breaks it for the creator, but not for the joiner of the lobby
                            //setLobby(lobby);
                        
                        } else {
                            System.out.println("Unknown class type received: " + message.getClass().getName());
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

        synchronized private void write(Command message) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * To be called by inner class thread messagehandling.
     *
     * @param lobby the new lobby to be set
     */
    private void setLobby(Lobby lobby) {
        this.lobby = lobby;
        setChanged();
        notifyObservers(lobby);
    }

    /**
     * Sends any command to the server.
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
     * Get the current lobby
     *
     * @return the current Lobby object
     */
    public Lobby getLobby() {
        while (this.lobby == null){
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.lobby;
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
     */
    public void sendMessage(String message) {
        send(new SendMessage(message));
    }

    /**
     * Updates player's movement to the server.
     *
     * @param type the user moving.
     * @param x the new x coordinate of the pod.
     * @param y the new y coordinate of the pod.
     */
    public void sendMovement(GameType type, float x, float y) {
        send(new SendMovement(type, x, y));
	System.out.println("Sent movement.");
    }

    /**
     * Gets all the lobbies from the server. Will block until the lobbies have
     * been received.
     *
     * @return a list containing all lobbies
     */
    public List<Lobby> getLobbies() {
        send(new GetLobbies());
        try {
            synchronized (lobbyList) {
                try {
                    lobbyList.wait();
                } catch (InterruptedException ex) {
                    System.out.println("Thread interrupted while waiting for object: " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lobbyList;
    }
}
