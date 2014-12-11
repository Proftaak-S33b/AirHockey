/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.rmi.Remote;
import java.util.HashMap;

/**
 *
 * @author Etienne u9
 */
public interface IServerData extends Remote {
    public HashMap<String, ClientData> getClients();
    public HashMap<String, String> k();
}
 