/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.Serializable;

/**
 *
 * @author Joris
 */
public interface IPlayer extends Serializable{

    public String getName();
    
    public int getRanking();
    
    public void setRanking(Boolean score);
}
