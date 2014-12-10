/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.IPlayer;
import org.jbox2d.common.Vec2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Tests the puck class. TODO returnwaardes, getters.
 *
 * @author Etienne
 */
public class MathUtillitiesTest {
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
     * Test of getPodSize method, of the class MathUtillities.
     */
    @Test
    public void testGetPodSize() {
        System.out.println("Testing getPodSize():");
        assertEquals("Returns incorrect pod size!", (double)(40*0.08), MathUtillities.getPodSize(), 0);
    }
    
    /**
     * Test of getPodRadius method of the class MathUtillities.
     */
    @Test
    public void testGetPodRadius() {
        System.out.println("Testing getPodRadius():");
        assertEquals("Returns incorrect pod radius!", (double)(40*0.08/2), MathUtillities.getPodRadius(), 0);
    }
    
    /**
     * Test of getPuckSize() method of the class MathUtillities.
     */
    @Test
    public void testGetPuckSize() {
        System.out.println("Testing getPuckSize():");
        assertEquals("Returns incorrect puck size!", (double)(40*0.04), MathUtillities.getPuckSize(), 0);
    }
}
