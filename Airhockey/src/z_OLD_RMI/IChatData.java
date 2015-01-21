/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z_OLD_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Joris
 */
public interface IChatData extends Remote {

    public boolean add(String message, IPlayer sender) throws RemoteException;

    public List<Message> getAll() throws RemoteException;
}
