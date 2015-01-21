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
public class RemoveLobby implements GameCommand {
    
    int id;
    
    public RemoveLobby(int id){
	this.id = id;
    }
    
    public GameReceiver receiver;

    /**
     * Execute removeLobby
     */
    @Override
    public void Execute() {
	//receiver.removeLobby(id);
    }       

    /**
     * set the receiver
     * @param receiver 
     */
    @Override
    public void SetReceiver(GameReceiver receiver) {
	this.receiver = receiver;
    }
}
