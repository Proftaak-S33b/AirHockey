/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.rmi.Remote;

/**
 *
 * @author Joris
 */
public interface IConnection extends Remote{

    public void update();
}
