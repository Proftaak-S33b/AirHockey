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
 * Tests the Human class implementing the Player interface.
 * @author Etienne
 */
public class HumanTest {
    
    private Human human;
    
    public HumanTest() {
        human = new Human("name", "password");
        
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
     * Test of getName method, of class Human.
     */
    @Test
    public void testGetName() {
        System.out.println("Testing Human.getName():");                
        assertEquals("Human.getName returned a different name than given.", human.getName(), "name");        
    }
}