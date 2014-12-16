/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import networking.IPlayer;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.Lobby;
import networking.Client;
import networking.ILobby;
import networking.Server;
import networking.standalone.ClientData;
import networking.standalone.IServerData;

/**
 * Controller for managing the multiplayer lobby with multiple games
 *
 * @author Joris
 */
public class LobbyManager {

    private final IServerData serverData;
    private final ObservableList<ClientData> clientData;
    private final Timer timer;
    private final Client client;
    private Server server = null;

    /**
     * Instantiates the lobbyController and sets a timer that will regularly
     * fetch the lobbies from the RMI server
     */
    public LobbyManager() {
        client = new Client();
        serverData = (IServerData) client.lookup("serverdata");
        clientData = FXCollections.observableArrayList();
        timer = new Timer("lobbyController", true);
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> {
        List<ClientData> lobs = new ArrayList<>();
        try {
            lobs = serverData.getClients();
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
        for(ClientData d : lobs){
            System.out.println("Lobby: " + d.getName() + d.getAddress().toString());
        }
        addClientDataIfNotPresent(lobs);
        removeClientDataIfDoesntExist(lobs);
//                });
    }

    private void removeClientDataIfDoesntExist(List<ClientData> lobs) {
        for (ClientData d : lobs) {
            ClientData tempData = null;
            for (ClientData data : clientData) {
                if (data.getName().equals(d.getName())) {
                    tempData = data;
                }
            }
            if (tempData != null) {
                clientData.remove(tempData);
            }
        }
    }

    private void addClientDataIfNotPresent(List<ClientData> lobs) {
        for (ClientData d : lobs) {
            ClientData tempData = null;
            for (ClientData data : clientData) {
                if (data.getName().equals(d.getName())) {
                    tempData = data;
                }
            }
            if (tempData != null) {
                clientData.add(tempData);
            }
        }
    }
//        }, 0, 1000);
//    }

    /**
     * Adds a new Lobby with specified name and host player
     *
     * @param gameName The name the new lobby will be identified as. Unique
     * @param host The IPlayer who created this lobby, cant be in another lobby
     * @return True if success, false if failed.
     */
    public ILobby addLobby(String gameName, IPlayer host) {
        try {
            server = new Server();
            final ILobby lobby = new Lobby(gameName, host);
            //bind to server
            server.bindToRegistry(lobby);
            serverData.add(InetAddress.getLocalHost(), gameName, null, host, null, null);
            return lobby;
        } catch (RemoteException | AlreadyBoundException | UnknownHostException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets the lobby with the specified name
     *
     * @param gameName The name of the lobby to return
     * @return Returns null if lobby not found.
     */
    public ClientData getLobby(String gameName) {
        for (ClientData d : clientData) {
            if (d.getName().equals(gameName)) {
                return d;
            }
        }
        return null;
    }

    public ILobby connect(ClientData server) {
        client.locateRegistry(server.getAddress().getHostAddress(), 1099);
        return (ILobby) client.lookup("hockeygame");
    }

    /**
     * Returns an unmodifiable ObservableList containing all lobbies in this
     * object
     *
     * @return All lobbies in this object
     */
    public ObservableList<ClientData> getLobbies() {
        return FXCollections.unmodifiableObservableList(clientData);
    }
}
