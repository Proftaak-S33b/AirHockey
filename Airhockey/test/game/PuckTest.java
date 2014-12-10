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
public class PuckTest {

    private Puck puck;
    private GameWorld world;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ObservableList<IPlayer> players = FXCollections.observableArrayList();
        players.add(new Human("A", "",0));
        players.add(new Human("B", "",0));
        players.add(new Human("C", "",0));
        world = new GameWorld(players);
        puck = new Puck(10, world);
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
     * Test of the getBody method of the class Puck.
     */
    @Test
    public void testGetBody() {
        System.out.println("Testing Puck.getBody():");
        assertNotNull("Body is null!", puck.getBody());
    }

}
