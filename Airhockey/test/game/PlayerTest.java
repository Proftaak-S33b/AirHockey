/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Player interface using a mock 
 * @author Etienne
 */
public class PlayerTest {
    
    private Player playerimpl;
    
    public PlayerTest() {
        playerimpl = new PlayerImpl();
        
        this.testGetName();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
    }

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName() {
        System.out.println("Testing Player.getName:");
        assertEquals("Player.getName did not return Player.Name.", "", playerimpl.getName());
    }

    public class PlayerImpl implements Player {

        public String getName() {
            return "";
        }
    }
}