/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import networking.IPlayer;
import networking.standalone.Connection;
import networking.standalone.Lobby;

/**
 *
 * @author Etienne
 */
public class ServerReceiver {

    private static int NEXTLOBBYID = 10000;

    private List<Lobby> lobbyList;
    private List<Connection> serverConnections;
    private LinkedBlockingQueue<Command> queue;

    public ServerReceiver() {
        lobbyList = new ArrayList<>();
        serverConnections = new ArrayList<>();
        queue = new LinkedBlockingQueue<>();

        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Command command = queue.take();
                        if (command instanceof ServerCommand) {
                            executeCommand((ServerCommand) command);
                        }
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    /**
     * Exectuce the command
     *
     * @param command The command to be executed
     */
    public void executeCommand(ServerCommand command) {
        command.setReceiver(this);
        command.execute();
        System.out.println("Executed command: " + command.getClass().getSimpleName());
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
     * Adds a lobby and puts the creator of this lobby in it.
     *
     * @param name the name of the lobby
     * @param host the host of the lobby
     * @param returnAddress the address to return the result to.
     */
    public void addLobby(String name, IPlayer host, Connection returnAddress) {
        Lobby lobby = new Lobby(NEXTLOBBYID, name, host);
        lobbyList.add(lobby);
        System.out.println("Adding lobbyID: " + NEXTLOBBYID);
        
        returnAddress.write(lobby);
        System.out.println("Sending new lobby ID: " + NEXTLOBBYID);
        NEXTLOBBYID++;
    }

    /**
     * Remove a lobby
     *
     * @param id The id of the lobby
     */
    public void removeLobby(int id) {
        Lobby lobby = null;
        for (Lobby l : lobbyList) {
            if (l.getID() == id) {
                lobby = l;
            }
        }
        if (lobby != null) {
            lobbyList.remove(lobby);
            System.out.println("Removing lobby: " + lobby.getGameName());
        }
    }

    /**
     * Change the name of the lobby
     *
     * @param id the id of the lobby
     * @param newName the new name of the lobby
     */
    public void changeLobbyName(int id, String newName) {
        for (Lobby l : lobbyList) {
            if (l.getID() == id) {
                l.setGameName(newName);
            }
        }
    }

    /**
     * Join a lobby add a player to the lobby
     *
     * @param id the id of the lobby
     * @param player the player which will join the lobby
     */
    public void joinLobby(int id, IPlayer player) {
        for (Lobby l : lobbyList) {
            if (l.getID() == id) {
                System.out.println("Lobby found");
                //l.addPlayer(player);
                //TODO
                //Make future commands sent by this user processed by GameReceiver
                //connection.setQueue(gameReceiver.getQueue);
            }
        }
    }

    /**
     * Sends the lobbylist over the specified connection.
     *
     * @param conn the connection to send the lobbylist over
     */
    public void getLobbies(Connection conn) {
        conn.write(lobbyList);
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
}
