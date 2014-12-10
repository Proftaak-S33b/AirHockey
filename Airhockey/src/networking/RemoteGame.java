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

    private final GameData data;
    /**
     * 
     * @throws RemoteException 
     */
    public RemoteGame() throws RemoteException {
        data = new GameData();
    }

    @Override
    public void setBluePodPos(Coordinate newPosition) throws RemoteException {
        data.setBluePodPos(newPosition.x, newPosition.y);
    }

    @Override
    public void setGreenPodPos(Coordinate newPosition) throws RemoteException {
        data.setGreenPodPos(newPosition.x, newPosition.y);
    }

    @Override
    public void setHostData(Coordinate newPodPos, Coordinate newPuckPos, Coordinate newPuckVel, int score1, int score2, int score3, int round) throws RemoteException {
        data.setRedPodPos(newPodPos.x, newPodPos.y);
        data.setPuckPos(newPuckPos.x, newPuckPos.y);
        data.setPuckVelocity(newPuckVel.x, newPuckVel.y);
        data.setScoreP1(score1);
        data.setScoreP2(score2);
        data.setScoreP3(score3);
        data.setRound(round);
    }
}
