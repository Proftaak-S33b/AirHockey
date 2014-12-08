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
    private Field field;
    private Pod pod;
    private Administrator admin;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        
        pod = new Pod(null, null, null);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Human("A", ""));
        players.add(new Human("B", ""));
        players.add(new Human("C", ""));
        gameworld = new GameWorld(players);
        field = new Field(gameworld,500);
        admin = Administrator.getInstance(players.get(0));
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
        assertNotNull("Gameworld does not contain any players: (Or does your playerlist start at 1?)", gameworld.getPlayer("A"));
    }
}
