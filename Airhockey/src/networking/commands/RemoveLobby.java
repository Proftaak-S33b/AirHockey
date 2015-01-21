/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import java.io.Serializable;

/**
 * Command object for removing a lobby from the server.
 * @author Etienne
 */
public class RemoveLobby implements GameCommand, Serializable{
    
    int id;
    
    public RemoveLobby(int id){
	this.id = id;
    }
    
    public GameReceiver receiver;

    @Override
    public void Execute() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }       

    @Override
    public void SetReceiver(GameReceiver receiver) {
	this.receiver = receiver;
    }
}
