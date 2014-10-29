/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the pod class.
 *
 * @author Etienne
 */
public class PodTest {

    private Pod pod;
    private Player player;
    private Vec2 position;
    private GameWorld world;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        player = new Human("name", "password");
        position = new Vec2(0, 0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Human("A", ""));
        players.add(new Human("B", ""));
        players.add(new Human("C", ""));
        world = new GameWorld(players);
        pod = new Pod(world, player, position);
    }

    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
    }

    /**
     * Test of getPlayer method, of class Pod.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("Testing Pod.getPlayer():");
        assertEquals("Player returned is not the same as it's field.", player, pod.getPlayer());
        assertNotNull("Pod.Player is null.", pod.getPlayer());
    }

    /**
     * Test of getPosition method, of class Pod.
     */
    @Test
    public void testGetPosition() {
        System.out.println("Testing Pod.getPosition():");
        assertEquals("Position incorrect", position, pod.getPosition());
        assertNotNull("Position not set.", pod.getPosition());
    }

    /**
     * Test of move method, of class Pod.
     */
    @Test
    public void testMove() {
        System.out.println("Testing Pod.Move():");
        pod.move(new Vec2(5, 5));
        assertEquals("Pod moved but position not the equalling.", position, pod.getPosition());
    }
}
