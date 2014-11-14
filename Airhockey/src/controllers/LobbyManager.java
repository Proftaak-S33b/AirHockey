/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import networking.IPlayer;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.ILobbyData;
import networking.Lobby;
import networking.RMIData;

/**
 * Controller for managing the multiplayer lobby with multiple games
 *
 * @author Joris
 */
public class LobbyManager {

    private final ILobbyData lobbyData;
    private final ObservableList<Lobby> lobbies;
    private final Timer timer;

    /**
     * Instantiates the lobbyController and sets a timer that will regularly
     * fetch the lobbies from the RMI server
     */
    public LobbyManager() {
        lobbyData = RMIData.getLobbyData();
        lobbies = FXCollections.observableArrayList();
        timer = new Timer("lobbyController", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    List<Lobby> lobs = new ArrayList<>();
                    try {
                        lobs = lobbyData.getAll();
                    } catch (RemoteException ex) {
                        System.out.println(ex.getMessage());
                    }
                    for (Lobby lobby1 : lobs) {
                        if (!lobbies.contains(lobby1)) {
                            lobbies.add(lobby1);
                        }
                    }
                    for (Lobby lobby2 : lobbies) {
                        if (!lobs.contains(lobby2)) {
                            lobbies.remove(lobby2);
                        }
                    }
                });
            }
        }, 0, 200);

    }

    /**
     * Adds a new Lobby with specified name and host player
     *
     * @param gameName The name the new lobby will be identified as. Unique
     * @param host The IPlayer who created this lobby, cant be in another lobby
     * @return True if success, false if failed.
     */
    public boolean addLobby(String gameName, IPlayer host) {
        try {
            return lobbyData.add(gameName, host);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
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
     * Returns an unmodifiable ObservableList containing all lobbies in this
     * object
     *
     * @return An unmodifiable ObservableList containing all lobbies in this
     * object
     */
    public ObservableList<Lobby> getLobbies() {
        return FXCollections.unmodifiableObservableList(lobbies);
    }
}
