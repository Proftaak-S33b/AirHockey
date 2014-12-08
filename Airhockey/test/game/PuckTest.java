/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import z_OLD_game.Human;
import z_OLD_game.Pod;
import z_OLD_game.Player;
import z_OLD_game.GameWorld;
import z_OLD_game.Puck;
import java.util.ArrayList;
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
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Human("A", ""));
        players.add(new Human("B", ""));
        players.add(new Human("C", ""));
        world = new GameWorld(players);
        puck = new Puck(world, 10);
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
     * Test of getDirection method, of class Puck.
     */
    @Test
    public void testGetDirection() {
        System.out.println("Testing Puck.getDirection():");
        assertTrue("getDirection returned geen coordinate.", puck.getVelocity() instanceof Vec2);
    }

    /**
     * Test of getLastTouched method, of class Puck.
     */
    @Test
    public void testGetLastTouched() {
        System.out.println("Testing Puck.getLastTouched():");
        Pod p = new Pod(world, new Human("name", "password"), new Vec2(0, 0));
        assertEquals("Pod verkeerd toegevoegd.", puck.getLastTouched(), p);
    }

    /**
     * Test of addTouched method, of class Puck.
     */
    @Test
    public void testAddTouched() {
        System.out.println("Testing Puck.addTouched():");
        puck = null;
        puck = new Puck(world, 10);
        Pod p = new Pod(world, new Human("name", "password"), new Vec2(0, 0));
        puck.addTouched(p);
        assertEquals("Toucher not added.", puck.getLastTouched(), p);
    }
}
