/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Joris
 */
public class RemoteGame extends UnicastRemoteObject implements IRemoteGame {

    private Coordinate redPod;
    private Coordinate bluePod;
    private Coordinate greenPod;
    private Coordinate puck;
    private Coordinate puckVelocity;
    private int scorePlayer1;
    private int scorePlayer2;
    private int scorePlayer3;
    private int round;
    /**
     * 
     * @throws RemoteException 
     */
    public RemoteGame() throws RemoteException {
        redPod = new Coordinate(0, 0);
        bluePod = new Coordinate(0, 0);
        greenPod = new Coordinate(0, 0);
        puck = new Coordinate(0, 0);
        puckVelocity = new Coordinate(0, 0);
        scorePlayer1 = 20;
        scorePlayer2 = 20;
        scorePlayer3 = 20;
        round = 0;
    }

    @Override
    public void setBluePodPos(Coordinate newPosition) throws RemoteException {
        bluePod.x = newPosition.x;
        bluePod.y = newPosition.y;
    }

    @Override
    public void setGreenPodPos(Coordinate newPosition) throws RemoteException {
        greenPod.x = newPosition.x;
        greenPod.y = newPosition.y;
    }

    @Override
    public void setHostData(Coordinate newPodPos, Coordinate newPuckPos, Coordinate newPuckVel, int score1, int score2, int score3, int round) throws RemoteException {
        redPod.x = newPodPos.x;
        redPod.y = newPodPos.y;
        puck.x = newPuckPos.x;
        puck.y = newPuckPos.y;
        puckVelocity.x = newPuckVel.x;
        puckVelocity.y = newPuckVel.y;
        scorePlayer1 = score1;
        scorePlayer2 = score2;
        scorePlayer3 = score3;
        this.round = round;
        
    }

    @Override
    public GameData getGameData() throws RemoteException {
        return new GameData(redPod, bluePod, greenPod, puck, puckVelocity, scorePlayer1, scorePlayer2, scorePlayer3, round);
    }
    
    
}
