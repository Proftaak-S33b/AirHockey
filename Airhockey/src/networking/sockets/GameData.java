/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.Serializable;

/**
 * The data object that contains the current information about the game
 * @author Jur
 */
public class GameData implements Serializable {
    public Coordinate redPod = new Coordinate(0,0);
    public Coordinate bluePod = new Coordinate(0,0);
    public Coordinate greenPod = new Coordinate(0,0);
    public Coordinate puck = new Coordinate(0,0);
    public Coordinate puckVelocity = new Coordinate(0,0);
    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;
    public int scorePlayer3 = 0;
    public int round = 0;
    
    /**
     * nothing
     */
    public GameData(){};
}
