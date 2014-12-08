/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import z_OLD_game.AI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the AI class that implements Player.
 * @author Etienne
 */
public class AITest {
    
    private AI ai;
    
    @BeforeClass
    public static void setUpClass() {        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ai = new AI("CPU1");
    }
    
    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
    }

    /**
     * Test of getName method, of class AI.
     */
    @Test
    public void testGetName() {
        System.out.println("Testing AI.GetName():");        
        assertEquals("AI.GetName returned a different name than given.", ai.getName(), "CPU1");
    }
}