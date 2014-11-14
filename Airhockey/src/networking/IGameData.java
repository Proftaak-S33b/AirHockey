/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.RemoteException;

/**
 *
 * @author Joris
 */
public interface IGameData {

    public void setRedPodPos(float x, float y) throws RemoteException;

    public void setBluePodPos(float x, float y) throws RemoteException;

    public void setGreenPodPos(float x, float y) throws RemoteException;

    public void setPuckPos(float x, float y) throws RemoteException;

    public void setPuckVelocity(float x, float y) throws RemoteException;

    public Coordinate getRedPodPos() throws RemoteException;

    public Coordinate getBluePodPos() throws RemoteException;

    public Coordinate getGreenPodPos() throws RemoteException;

    public Coordinate getPuckPos() throws RemoteException;

    public Coordinate getPuckVelocity() throws RemoteException;
}
