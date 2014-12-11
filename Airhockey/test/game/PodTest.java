/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.*;
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
    private IPlayer player;
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
        player = new Human("name", "password", 0);
        position = new Vec2(0, 0);
        ObservableList<IPlayer> players = FXCollections.observableArrayList();
        players.add(new Human("A", "", 0));
        players.add(new Human("B", "", 0));
        players.add(new Human("C", "", 0));
        world = new GameWorld(players);
        pod = new Pod(world, player, position);
    }

    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
    }
    
    /**
     * Test the getPlayer method of the class Pod.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("Testing getPlayer():");
        assertNotNull("Player is null!", pod.getPlayer());
        assertEquals("Player is not set correctly", pod.getPlayer(), player);
    }
    
    /**
     * Test the getPosition method of the class Pod.
     */
    @Test
    public void testGetPosition() {
        System.out.println("Testing getPosition():");
        assertEquals("Position is not set correctly!", pod.getPosition(), position);
    }
    
    /**
     * Test the moveLeft and moveRight methods of the class Pod
     */
    @Test
    public void testMoveMethods() {
        System.out.println("Testing move methods:");
        pod.moveRight(0);
        assertTrue("Pod doesn't move right", pod.getPosition() != position);
        pod.moveLeft(0);pod.moveLeft(0);
        assertTrue("Pod doesn't move left", pod.getPosition() != position);
    }
    
    /**
     * Test the setPosition method of the pod class.
     */
    @Test
    public void testSetPosition() {
        System.out.println("Testing setPosition method:");
        pod.setPosition(position);
        assertEquals("Position isn't set correctly", position, pod.getPosition());
    }
}
