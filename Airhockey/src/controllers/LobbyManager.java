/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import networking.IPlayer;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import networking.sockets.Client;
import networking.standalone.Lobby;
import networking.standalone.rmiDefaults;

/**
 * Controller for managing the multiplayer lobby with multiple games
 *
 * @author Joris
 */
public class LobbyManager implements Observer {

    private Timer timer;
    private Client client = null;
    private ListView chatBox;
    private IPlayer player;
    private final ObservableList<Lobby> lobbies;

    /**
     * Instantiates the lobbyController and sets a timer that will regularly
     * fetch the lobbies from the RMI server
     *
     * @param chatBox the ListView to put new chat messages in
     * @param player
     */
    public LobbyManager(ListView chatBox) {
        this.chatBox = chatBox;
        this.player = null;
        this.lobbies = FXCollections.observableArrayList();
        try {
            System.out.println("Connecting...");
            client = new Client(rmiDefaults.DEFAULT_SERVER_IP(), rmiDefaults.DEFAULT_PORT());
            client.addObserver(this);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        timer = new Timer("lobbyController", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    List<Lobby> lobs;
                    lobs = client.getLobbies();
                    for (Lobby l : lobs) {
                        System.out.println("Lobby: " + l.getGameName());
                    }
                    lobbies.clear();
                    lobbies.addAll(lobs);
                    System.out.println(lobbies.size() + " lobbies in list");
//        addClientDataIfNotPresent(lobs);
//        removeClientDataIfDoesntExist(lobs);
                });
            }
        }, 0, 5000);
    }
    //    private void removeClientDataIfDoesntExist(List<ClientData> lobs) {
    //        for (ClientData d : lobs) {
    //            ClientData tempData = null;
    //            for (ClientData data : clientData) {
    //                if (data.getName().equals(d.getName())) {
    //                    tempData = data;
    //                }
    //            }
    //            if (tempData != null) {
    //                clientData.remove(tempData);
    //            }
    //        }
    //    }
    //
    //    private void addClientDataIfNotPresent(List<ClientData> lobs) {
    //        for (ClientData d : lobs) {
    //            ClientData tempData = null;
    //            for (ClientData data : clientData) {
    //                if (data.getName().equals(d.getName())) {
    //                    tempData = data;
    //                }
    //            }
    //            if (tempData != null) {
    //                clientData.add(tempData);
    //            }
    //        }
    //    }
    //        }, 0, 1000);
    //    }

    /**
     * Adds a new Lobby with specified name and host player and joins it.
     *
     * @param gameName The name the new lobby will be identified as. Unique
     * @param host The IPlayer who created this lobby, cant be in another lobby
     */
    public void addLobby(String gameName, IPlayer host) {
        client.addLobby(gameName, host);
    }

    /**
     * Gets the lobby with the specified name
     *
     * @param gameName The name of the lobby to return
     * @return Returns null if lobby not found.
     */
    public Lobby getLobby(String gameName) {
        for (Lobby l : lobbies) {
            if (l.getGameName().equals(gameName)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Tells the server to assign this player to the specified lobby.
     *
     * @param lobby The lobby to join
     * @param player The player in question
     */
    public void joinLobby(Lobby lobby, IPlayer player) {
        client.joinLobby(lobby, player);
    }

    /**
     *
     * @return
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Returns an unmodifiable ObservableList containing all lobbies in this
     * object
     *
     * @return All lobbies in this object
     */
    public ObservableList<Lobby> getLobbies() {
        return FXCollections.unmodifiableObservableList(lobbies);
    }

    /**
     *
     * @param message
     */
    public void sendChat(String message) {
        client.sendMessage(message);
    }

    /**
     *
     */
    public void destroy() {
        timer.cancel();
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof String) {
            Platform.runLater(() -> {
                chatBox.getItems().add(o1);
            });
        }
    }
}
