/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.lang.reflect.Array;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the GameWorld class.
 * Let op: Sommige objecten moeten misschien vervangen worden door mocks.
 *         Hier hebben we nog geen les over gehad dus dit is nog niet toegepast.
 * @author Etienne
 */
public class GameWorldTest {
    
    private GameWorld gameworld;
    private Field field;
    private Pod pod;
    private Administrator admin;
            
    public GameWorldTest() {
        field = new Field(500);
        pod = new Pod(null, null);
        Player players[] = new Player[] {new Human("A", ""), new Human("B", ""), new Human("B", "")};
        gameworld = new GameWorld(players);       
        admin = Administrator.getInstance(players[0]);
        
        this.testGetPlayer();
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
    }

    /**
     * Test of getPlayer method, of class GameWorld.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("Testing GameWorld.getPlayer():");        
        assertNotNull("Gameworld does not contain any players: (Or does your playerlist start at 1?)", gameworld.getPlayer(0));
    }
}