/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import z_OLD_game.Difficulty;
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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        diff = Difficulty.NORMAL;
    }
    
    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
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