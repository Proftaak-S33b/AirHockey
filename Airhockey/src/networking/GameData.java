/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Joris
 */
public class GameData extends UnicastRemoteObject implements Serializable {

    private float xRedPod;
    private float yRedPod;

    public GameData() throws RemoteException {

    }
}
