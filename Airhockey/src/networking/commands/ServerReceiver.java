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

    public ServerReceiver() {
        lobbyList = new ArrayList<>();
        serverConnections = new ArrayList<>();
        Thread messageHandling = new Thread() {
            @Override
            public void run() {
                while (true) {
                    for (Connection c : serverConnections) {
                        ServerCommand command;
                        if ((command = c.read()) != null) {
                            executeCommand(command);
                        }
                    }
                    System.out.println("Message received");
                }
            }
        };
        messageHandling.setDaemon(true);
        messageHandling.start();
    }

    public void executeCommand(ServerCommand command) {
        //pass reference to command object
        command.Execute();
    }

    public void addLobby(String name, IPlayer host) {

    }

    public void removeLobby(int id) {

    }

    public void changeLobbyName(int id, String newName) {
        for (Lobby l : lobbyList) {
            if (l.getID() == id) {
                l.setGameName(newName);
            }
        }
    }

    public void joinLobby(int id, IPlayer player) {
        for (Lobby l : lobbyList) {
            if (l.getID() == id) {
                l.addPlayer(player);
                //TODO
                //Make future commands sent by this user processed by GameReceiver
            }
        }
    }
}
