/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import networking.IPlayer;
import org.jbox2d.common.Vec2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * TODO
 *
 * @author Etienne
 */
public class GoalTest {

    private Goal goal;
    private IPlayer players;
    private GameWorld gameworld;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ObservableList<IPlayer> players = FXCollections.observableArrayList();
        players.add(new Human("A", "", 0));
        players.add(new Human("B", "", 0));
        players.add(new Human("C", "", 0));
        gameworld = new GameWorld(players);
        goal = new Goal(gameworld.getPlayer("A"), gameworld, new Vec2(0,0), new Vec2(0,0));
    }

    @After
    public void tearDown() {
        System.out.println("Finished testing " + this.toString() + ".\n");
    }

    /**
     * Test of getPlayer method, of class Goal.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("Testing goal.getPlayer():");
        assertNotNull("Player is null.", goal.getPlayer());
        assertEquals("Player isn't set correctly.", 
                goal.getPlayer(), 
                gameworld.getPlayer("A"));
    }
}
