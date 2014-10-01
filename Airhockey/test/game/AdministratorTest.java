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
 * Tests the administrator singleton class.
 * @author Etienne
 */
public class AdministratorTest {
    
    public Administrator admin;
    public Player player;
    
    public AdministratorTest() {
        player = new Human("username", "password");
        admin = Administrator.getInstance(player);
        
        this.testGetInstance();
        this.testGetPlayer();
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
     * Test of getInstance method, of class Administrator.
     */
    @Test
    public void testGetInstance() {
        System.out.println("Testing Administrator.getInstance:");     
        assertNotNull("Admin.getInstance() returned an empty instance.", Administrator.getInstance(player));
        assertEquals("Wrong instance returned.", player, Administrator.getInstance(player));
    }

    /**
     * Test of getPlayer method, of class Administrator.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("Testing Administrator.getPlayer:");
        admin.getPlayer();
        assertEquals("Player does not equal admin.getPlayer().", player, admin.getPlayer());
    }
}