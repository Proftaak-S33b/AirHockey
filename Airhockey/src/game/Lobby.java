/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Joris
 */
public class Lobby {

    private SimpleStringProperty gameName;
    private SimpleIntegerProperty playersAmount;
    private SimpleIntegerProperty hostScore;

    private ArrayList<IPlayer> players;

    public Lobby(String gameName, IPlayer host) {
        this.gameName.set(gameName);
        this.playersAmount.set(1);
        this.hostScore.set(host.getScore());
    }

    public String getGameName() {
        return gameName.get();
    }

    public void setGameName(String gameName) {
        this.gameName.set(gameName);
    }

    public ArrayList<IPlayer> getPlayers() {
        return players;
    }

    public int getPlayersAmount() {
        return players.size();
    }
}
