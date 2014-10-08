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
 * Tests the Coordinate class used by the pods and pucks.
 * @author Etienne
 */
public class CoordinateTest {
    
    private Coordinate c;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        c = new Coordinate(0,0);
    }
    
    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
    }

    /**
     * Test of toString method, of class Coordinate.
     */
    @Test
    public void testToString() {
        System.out.println("Coordinate.toString():");
        assertNotNull("String is null.", c.toString());
        assertSame("No string returned.", "", c.toString());        
    }
}