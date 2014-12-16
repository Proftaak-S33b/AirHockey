/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Testing class for debugging of RMI communications.
 * @author Etienne
 */
public class TestData extends UnicastRemoteObject implements Remote {
    public int integer = 5;
    public String string = "TestData";
    
    public TestData() throws RemoteException {
    }
    
    public String getString() throws RemoteException{
	return string;
    }
    
    public void setString(String value) throws RemoteException {
	string = value;
    }
}
