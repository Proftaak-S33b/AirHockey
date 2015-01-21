/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets_old;

import networking.IPlayer;

/**
 *
 * @author Joris
 */
public class Message {
    
    private String text;
    private IPlayer sender;
    
    public Message(String text, IPlayer sender){
        this.text = text;
        this.sender = sender;
    }
    
    @Override
    public String toString(){
        return sender.getName() + ": " + text;
    }
}
