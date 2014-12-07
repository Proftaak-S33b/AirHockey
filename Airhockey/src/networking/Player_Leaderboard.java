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
    
    /**
     * Creates a player object that can be used in the leaderboard
     * @param Nr The rank of the player
     * @param Name The name of the player
     * @param Ranking The ranking score of the player
     */
    public Player_Leaderboard(int Nr, String Name, String Ranking){
        nr = Nr;
        name = Name;
        ranking = Ranking;
    }
    /**
     * Gets the ranking of the player
     * @return The ranking of the player
     */
    public String getRanking(){
        return ranking;
    }
    /**
     * Gets the name of the player
     * @return The name of the player
     */
    public String getPlayerName(){
        return name;
    }
    /**
     * Gets the overall rank of the player
     * @return The nr of the rank
     */
    public String getNr(){
        return "" + nr;
    }
}
