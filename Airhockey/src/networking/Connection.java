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
public class Connection extends UnicastRemoteObject implements IConnection {

    private float xRedPod;
    private float yRedPod;

    public Connection() throws RemoteException {

    }

    @Override
    public void update() {

    }

}
