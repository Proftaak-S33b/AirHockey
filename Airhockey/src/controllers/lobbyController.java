/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.IPlayer;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.Lobby;

/**
 * Controller for managing the multiplayer lobby with multiple games
 *
 * @author Joris
 */
public class LobbyController implements Serializable{

    private transient ObservableList<Lobby> lobbiesObservable;
    private final ArrayList<Lobby> lobbies;

    public LobbyController() {
        lobbies = new ArrayList<>();
        lobbiesObservable = FXCollections.observableArrayList(lobbies);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        lobbiesObservable = FXCollections.observableArrayList(lobbies);
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
        lobbies.clear();
        for(Lobby l : lobbiesObservable){
            lobbies.add(l);
        }
    }

    public void addLobby(String gameName, IPlayer host) {
        Lobby l = new Lobby(gameName, host);
        lobbiesObservable.add(l);
    }

    public Lobby getLobby(String gameName) {
        for (Lobby l : lobbiesObservable) {
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
