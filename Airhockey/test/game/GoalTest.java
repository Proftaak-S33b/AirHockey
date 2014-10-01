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
 * TODO
 * @author Etienne
 */
public class GoalTest {
    
    private Goal goal;
    private Player player;
    private Coordinate position;
    
    public GoalTest() {
        position = new Coordinate(0,0);
        player = new Human("name", "password");
        goal = new Goal(position, 275, player);
        
        this.testGetPlayer();
        this.testGetPosition();
        this.testGetSize();
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
     * Test of getPosition method, of class Goal.
     */
    @Test
    public void testGetPosition() {
        System.out.println("Testing Goal.getPosition():");
        Coordinate check = new Coordinate(0,0);
        assertEquals("Incorrect coordinates.", goal.getPosition(), check);
    }

    /**
     * Test of getSize method, of class Goal.
     */
    @Test
    public void testGetSize() {
        System.out.println("Testing goal.getSize():");
        assertEquals("Incorrect goalsize returned.", goal.getSize(), 275);
    }

    /**
     * Test of getPlayer method, of class Goal.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("Testing goal.getPlayer():");
        assertNotNull("Player is null.", goal.getPlayer());
        assertEquals("Player isn't set correctly.", goal.getPlayer(), player);        
    }
}