/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.sockets;

import java.io.Serializable;

/**
 * Object that conatains Coordinate data
 * @author Jur
 */
public class Coordinate implements Serializable {

    public float x;
    public float y;

    /**
     * Create a new coordinate object with the given info
     * @param x The value on the x-axis
     * @param y The value on the y-axis
     */
    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
