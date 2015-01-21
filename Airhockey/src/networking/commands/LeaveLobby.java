/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import networking.standalone.Server;

/**
 *
 * @author Etienne
 */
public class LeaveLobby implements Command{
    Server server; 
    
    public LeaveLobby(Server server){
	this.server = server;
    }
    
    @Override
    public void Execute() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
