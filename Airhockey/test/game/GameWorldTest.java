/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import z_OLD_game.Human;
import z_OLD_game.Field;
import z_OLD_game.Pod;
import z_OLD_game.Administrator;
import z_OLD_game.Player;
import z_OLD_game.GameWorld;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the GameWorld class. Let op: Sommige objecten moeten misschien
 * vervangen worden door mocks. Hier hebben we nog geen les over gehad dus dit
 * is nog niet toegepast.
 *
 * @author Etienne
 */
public class GameWorldTest {

    private GameWorld gameworld;

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
        gameworld = new GameWorld(players);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test the getPlayer() method of class GameWorld.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("Testing GameWorld(players):");
        assertEquals("Non excisting playername returns a object!",
                null,
                gameworld.getPlayer("non excisting"));
        assertNotNull("Excisting playername returns no player",
                gameworld.getPlayer("A"));
    }
    
    /**
     * Test the getPhysWorld() method of class GameWorld.
     */
    @Test
    public void testGetPhysWorld() {
        System.out.println("Testing getPhysWorld:");
        assertNotNull("Returns null!",
                gameworld.getPhysWorld());
    }
    
    /**
     * Test the getPuck() method of the class GameWorld.
     */
    @Test
    public void testGetPuck() {
        System.out.println("Testing getPuck:");
        assertNotNull("Returns null!",
                gameworld.getPuck());
    }
    
    /**
     * Test the getPod() method of the class GameWorld.
     */
    @Test
    public void testGetPod() {
        System.out.println("Testing getPod:");
        assertNotNull("Returns null!", 
                gameworld.getPod(1));
        try
        {
            gameworld.getPod(100);
            assertFalse("getPod() outside of index doesn't throw a error!", true);
        }
        catch(Exception e)
        {
            assertFalse("getPod() outside of index doesn't throw a error!", false);
        }
    }
    
    /**
     * Test the findPlayerIndex method of the class GameWorld.
     */
    @Test
    public void testFindPlayerIndex() throws NoSuchFieldException {
        System.out.println("Testing findPlayerIndex:");
        assertEquals("Index doesn't match!",
                0,
                gameworld.findPlayerIndex("A"));
    }
}
