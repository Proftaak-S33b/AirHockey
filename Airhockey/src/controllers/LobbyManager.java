/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import networking.IPlayer;
import java.util.List;
import java.util.Timer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import networking.Lobby;
import networking.Client;
import networking.ILobby;
import networking.Server;
import networking.standalone.ChatSocketClient;
import networking.standalone.ClientData;
import networking.standalone.IClientData;
import networking.standalone.IServerData;
import networking.standalone.rmiDefaults;

/**
 * Controller for managing the multiplayer lobby with multiple games
 *
 * @author Joris
 */
public class LobbyManager implements ChangeListener<String> {

    public final IServerData serverData;
    private final ObservableList<ClientData> clientData;
    private final Timer timer;
    private final Client client;
    private Server server = null;
    private ChatSocketClient chat;
    private ObservableList<String> messages;
    private ListView chatBox;

    /**
     * Instantiates the lobbyController and sets a timer that will regularly
     * fetch the lobbies from the RMI server
     *
     * @param chatBox the ListView to put new chat messages in
     */
    public LobbyManager(ListView chatBox) {
        this.chatBox = chatBox;
        try {
            chat = new ChatSocketClient(rmiDefaults.DEFAULT_SERVER_IP(), 69, this);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }
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
        for (ClientData d : lobs) {
            System.out.println("Lobby: " + d.getName() + d.getAddress().toString());
        }
        clientData.addAll(lobs);
        //addClientDataIfNotPresent(lobs);
        //removeClientDataIfDoesntExist(lobs);
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

    public ILobby connect(IClientData server) {
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

    public void sendChat(String message) {
        chat.send(message);
    }

    public void destroy() {
        timer.cancel();
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Platform.runLater(() -> {
            chatBox.getItems().add(newValue);
        });
    }
}
