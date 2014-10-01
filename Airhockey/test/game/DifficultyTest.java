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
 *
 * @author Etienne
 */
public class DifficultyTest {

    private Difficulty diff;
    
    public DifficultyTest() {
        diff = Difficulty.NORMAL;
        
        this.testGetOmschr();
        this.testToString();
        this.testValueOf();
        this.testValues();
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
     * Test of values method, of class Difficulty.
     */
    @Test
    public void testValues() {
        System.out.println("Testing Difficulty.values():");
        assertFalse("No constants declared.", diff.values().length == 0);
        
    }

    /**
     * Test of valueOf method, of class Difficulty.
     */
    @Test
    public void testValueOf() {
        System.out.println("Testing Difficulty.valueOf():");
        assertNotNull("valueOf() is null.", diff.valueOf("NORMAL"));
    }

    /**
     * Test of getOmschr method, of class Difficulty.
     */
    @Test
    public void testGetOmschr() {
        System.out.println("getOmschr");
        assertTrue("Difficulty.ToString did not return a String.", diff.toString() instanceof String);
        assertNotNull("Omschrijving is null.", diff.toString());
    }

    /**
     * Test of toString method, of class Difficulty.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        for (Difficulty d : Difficulty.values()) {
            assertTrue("Difficulty.ToString did not return a String.", d.toString() instanceof String);
            assertFalse("Difficulty.ToString is empty.", "".equals(d.toString()));
        }
    }
}