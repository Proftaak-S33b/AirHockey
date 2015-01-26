/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import java.util.ArrayList;
import java.util.List;
import networking.IPlayer;
import networking.standalone.Connection;
import networking.standalone.Lobby;

/**
 *
 * @author Etienne
 */
public class ServerReceiver extends Receiver {

    private static int NEXTLOBBYID = 10000;

    private List<LobbyReceiver> lobbyList;

    /**
     * 
     */
    public ServerReceiver() {
        Receiver();
        lobbyList = new ArrayList<>();

        Thread messageHandler = new Thread() {
            @Override
            public void run() {
                while (running) {
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
        messageHandler.setDaemon(true);
        messageHandler.start();
    }

    /**
     * Exectuce the command
     *
     * @param command The command to be executed
     */
    public void executeCommand(ServerCommand command) {
        command.setReceiver(this);
        command.execute();
        System.out.println("Executed ServerCommand: " + command.getClass().getSimpleName());
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
        System.out.println("Adding lobbyID: " + NEXTLOBBYID);
        LobbyReceiver lobbyReceiver = new LobbyReceiver(lobby);
        lobbyReceiver.addConnection(returnAddress);
        this.removeConnection(returnAddress);
        lobbyList.add(lobbyReceiver);
        returnAddress.setQueue(lobbyReceiver.getQueue());
        returnAddress.write(lobby);
        System.out.println("Sending new lobby ID: " + NEXTLOBBYID);
        NEXTLOBBYID++;
    }

    /**
     * Remove a lobby, the lobby can't have any players in it.
     *
     * @param id The id of the lobby
     */
    public void removeLobby(int id) {
        LobbyReceiver lobbyReceiver = null;
        for (LobbyReceiver lr : lobbyList) {
            if (lr.getLobby().getID() == id) {
                lobbyReceiver = lr;
            }
        }
        if (lobbyReceiver != null) {
            if (lobbyReceiver.getLobby().getPlayersAmount() != 0) {
                lobbyList.remove(lobbyReceiver);
                System.out.println("Removing lobby: " + lobbyReceiver.getLobby().getGameName());
            }
        }
    }

    /**
     * Change the name of the lobby
     *
     * @param id the id of the lobby
     * @param newName the new name of the lobby
     * @deprecated not implemented
     */
    public void changeLobbyName(int id, String newName) {
        //Could
        //todo
    }

    /**
     * Join a lobby add a player to the lobby
     *
     * @param id the id of the lobby
     * @param player the player which will join the lobby
     * @param conn the associated connection that will have its incoming
     * commands processed by the lobbyreceiver you are joining.
     */
    public void joinLobby(int id, IPlayer player, Connection conn) {
        System.out.println(player.getName() + " joining...");
        for (LobbyReceiver lr : lobbyList) {
            if (lr.getLobby().getID() == id) {
                System.out.println("Lobby found");
                lr.addConnection(conn);
                serverConnections.remove(conn);
                lr.getLobby().addPlayer(player);
                conn.setQueue(lr.getQueue());
                conn.write(lr.getLobby());
            }
        }
    }

    /**
     * Sends a list of lobbies over the specified connection.
     *
     * @param conn the connection to send the lobbylist over
     */
    public void getLobbies(Connection conn) {
        List<Lobby> lobby = new ArrayList<>();
        for (LobbyReceiver lr : lobbyList) {
            lobby.add(lr.getLobby());
        }
        conn.write(lobby);
    }
}
