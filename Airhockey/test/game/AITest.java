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
 * Tests the AI class that implements Player.
 * @author Etienne
 */
public class AITest {
    
    private AI ai;
    
    public AITest() {
        ai = new AI("CPU1");
        
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
     * Test of getName method, of class AI.
     */
    @Test
    public void testGetName() {
        System.out.println("Testing AI.GetName():");        
        assertEquals("AI.GetName returned a different name than given.", ai.getName(), "CPU1");
    }
}