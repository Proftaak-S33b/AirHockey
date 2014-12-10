/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Testing class for debugging of RMI communications.
 * @author Etienne
 */
public class TestData implements Remote, Serializable{
    public int integer = 5;
    public String string = "TestData";
    
    public String getString(){
	return string;
    }
    
    public void setString(String value){
	string = value;
    }
}
