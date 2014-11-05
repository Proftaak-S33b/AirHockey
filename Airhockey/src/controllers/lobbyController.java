/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.IPlayer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import shared.Lobby;

/**
 * Controller for managing the multiplayer lobby with multiple games
 *
 * @author Joris
 */
public class lobbyController {

    private transient ObservableList<Lobby> lobbiesObservable;
    private final ArrayList<Lobby> lobbies;

    public lobbyController() {
        lobbies = new ArrayList<>();
        lobbiesObservable = FXCollections.observableList(lobbies);
    }

    private void readObject(ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        lobbiesObservable = FXCollections.observableList(lobbies);
    }

    public void addLobby(String gameName, IPlayer host) {
        lobbies.add(new Lobby(gameName, host));
    }

    public Lobby getLobby(String gameName) {
        for (Lobby l : lobbies) {
            if (l.getGameName().equals(gameName)) {
                return l;
            }
        }
        return null;
    }

    public ObservableList<Lobby> getLobbies() {
        return FXCollections.unmodifiableObservableList(lobbiesObservable);
    }
}
