/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.Human;
import networking.IPlayer;
import networking.standalone.Lobby;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maikel
 */
//Can't test in console when there is a view object needed for constructor...
/*
public class LobbyManagerTest {
    LobbyManager lm;
    IPlayer player;
    public LobbyManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        lm = new LobbyManager();
        player =  new Human("Bob1", "1234", 20);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void addLobbyTest()
    {
        //Huilen
        assertTrue("Add lobby",lm.addLobby("Lobby1", player));
        assertFalse("Add lobby without name",lm.addLobby("", player));
        assertFalse("Add lobby without player",lm.addLobby("Lobby1", null));
    }
    
    @Test
    public void getLobbyTest()
    {     
        lm.addLobby("Lobby2", player);
        
        assertTrue("get lobby",lm.getLobby("Lobby2") instanceof Lobby);
        assertFalse("get lobby without name", lm.getLobby("") == null);
    }
}
*/
