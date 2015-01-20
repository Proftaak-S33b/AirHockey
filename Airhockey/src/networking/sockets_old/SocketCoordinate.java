/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets_old;

import networking.Coordinate;

/**
 *
 * @author Etienne
 */
public class SocketCoordinate extends Coordinate{

    public Name name;
    
    public enum Name{
	RED,
	GREEN,
	BLUE,
	PUCK
    }    
	    
    public SocketCoordinate(float x, float y, Name name) {
	super(x, y);
	this.name = name;
    }            
}
