/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import networking.IPlayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.Lobby;
import networking.LobbyData;
import networking.RMIData;

/**
 * Controller for managing the multiplayer lobby with multiple games
 *
 * @author Joris
 */
public class LobbyManager {

    private LobbyData lobbyData;
    private ObservableList<Lobby> lobbies;
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
                ArrayList<Lobby> lobs = lobbyData.getAll();
                lobbies.clear();
                lobbies.addAll(lobs);
            }
        }, 0, 100);
    }

    /**
     * Adds a new Lobby with specified name and host player
     *
     * @param gameName The name the new lobby will be identified as. Unique
     * @param host The IPlayer who created this lobby
     * @return True if success, false if failed.
     */
    public boolean addLobby(String gameName, IPlayer host) {
        lobbies.clear();
        lobbies.addAll(lobbyData.getAll());
        for (Lobby l : lobbies) {
            if (l.getGameName().equals(gameName)) {
                return false;
            }
        }
        return lobbyData.add(gameName, host);
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