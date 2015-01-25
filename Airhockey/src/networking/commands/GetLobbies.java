/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import networking.standalone.Connection;

/**
 *
 * @author Joris
 */
public class GetLobbies implements ServerCommand, ReturnCommand {

    private Connection returnAddress;
    
    /**
     * Does nothing. Please make sure 'setReturnAddress' is used server-side.
     */
    public GetLobbies(){
        
    }
    
    /**
     * Sends the list of lobbies back to where this command came from
     */
    @Override
    public void execute() {
        receiver.getLobbies(returnAddress);
    }
    
    ServerReceiver receiver;

    @Override
    public void setReceiver(ServerReceiver receiver) {
        this.receiver = receiver;
                
    }
    
    /**
     * Only to be used server-side please! Updates the return connection for return value.
     * @param conn the returnaddress
     */
    @Override
    public void setReturnAddress(Connection conn){
        returnAddress = conn;
    }
}
