/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

/**
 *Object that keeps record of the player info for the leaderboard
 * @author Jur
 */
public class Player_Leaderboard {
    private final int nr;
    private final String name;
    private final String ranking;
    
    public Player_Leaderboard(int Nr, String Name, String Ranking){
        nr = Nr;
        name = Name;
        ranking = Ranking;
    }
    public String getRanking(){
        return ranking;
    }
    public String getPlayerName(){
        return name;
    }
    public String getNr(){
        return "" + nr;
    }
}
