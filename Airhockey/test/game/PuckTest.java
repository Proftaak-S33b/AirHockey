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
import org.junit.Ignore;

/**
 * Tests the puck class.
 * TODO returnwaardes, getters.
 * @author Etienne
 */
public class PuckTest {
    
    private Puck puck;
    
    public PuckTest() {
        puck = new Puck(10);
        
        this.testAddTouched();
        this.testGetDirection();
        this.testGetLastTouched();
        this.testGetPosition();
        this.testMove();
        this.testSetSpeed();
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
     * Test of getPosition method, of class Puck.
     */
    @Test
    public void testGetPosition() {
        System.out.println("Testing Puck.getPosition():");        
        assertNotNull("Position is null.", puck.getPosition());        
    }

    /**
     * Test of move method, of class Puck.
     */
    @Test
    public void testMove() {
        System.out.println("Testing Puck.move():");
        
    }

    /**
     * Test of setSpeed method, of class Puck.
     */
    @Ignore(value = "Speed valt nergens uit af te leiden.")
    @Test
    public void testSetSpeed() {
        System.out.println("Testing Puck.setSpeed():");
        //TODO
    }

    /**
     * Test of getDirection method, of class Puck.
     */
    @Test
    public void testGetDirection() {
        System.out.println("Testing Puck.getDirection():");
        assertTrue("getDirection returned geen coordinate.", puck.getDirection() instanceof Coordinate);
    }

    /**
     * Test of getLastTouched method, of class Puck.
     */
    @Test
    public void testGetLastTouched() {
        System.out.println("Testing Puck.getLastTouched():");
        Pod p = new Pod(new Human("name", "password"), new Coordinate(0,0));                
        assertEquals("Pod verkeerd toegevoegd.", puck.getLastTouched(), p);
    }

    /**
     * Test of addTouched method, of class Puck.
     */
    @Test
    public void testAddTouched() {
        System.out.println("Testing Puck.addTouched():");
        puck = null;
        puck = new Puck(10);
        Pod p = new Pod(new Human("name", "password"), new Coordinate(0,0));                
        puck.addTouched(p);
        assertEquals("Toucher not added.", puck.getLastTouched(), p);
    }
}