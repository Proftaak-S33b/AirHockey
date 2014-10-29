/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Field class, the playing field for the players.
 * @author Etienne
 */
public class FieldTest {
    
    private Field field;
    private GameWorld gw;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Human("Henk", "fiets"));
        players.add(new AI("Player 2"));
        players.add(new AI("Player 3"));
        gw = new GameWorld(players);
        field = new Field(gw, 500);
    }
    
    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
    }

    /**
     * Test of getSize method, of class Field.
     */
    @Test
    public void testGetSize() {
        System.out.println("Testing Field.getSize():");
        assertTrue("Incorrect fieldsize.", field.getSize() == 500);
    }

    /**
     * Test of setSize method, of class Field.
     */
    @Test
    public void testSetSize() {
        System.out.println("Testing Field.setSize():");
        field.setSize(800);
        assertTrue("Fieldsize niet geset.", field.getSize() == 800);
    }

    /**
     * Test of getGoalCorners method, of class Field.
     */
    @Ignore("Not sure what to do here.")
    @Test
    public void testGetGoalCorners() {
        System.out.println("getGoalCorners");
        double x1 = 0.0;
        double y1 = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        double sizeX = 0.0;
        double sizeY = 0.0;
        Field instance = new Field(gw,500);
        ArrayList expResult = null;
        ArrayList result = instance.getGoalCorners(x1, y1, x2, y2, sizeX, sizeY);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}