/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.GameManager.GameType;
import game.AI.AI;
import game.AI.Difficulty;
import game.GameWorld;
import game.Human;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import networking.IPlayer;
import org.jbox2d.common.Vec2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maikel
 */
public class GameManagerTest {
    GameManager gm;
    GraphicsContext gc;
    GameWorld gw;
    private ObservableList<IPlayer> players;
    public GameManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        players = FXCollections.observableArrayList();
        players.add(new Human("ik","w8woord", 20));
        players.add(new AI("Blue", 20));
        players.add(new AI("Green", 20));
        gm = new GameManager(gc, players, Difficulty.EASY,GameType.SINGLEPLAYER , null, null);
        gw = new GameWorld(players);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void player_moveTest()
    {
        Vec2 vector;
        Vec2 vector1;
        vector = gw.getPod(0).getPosition();
        gm.player_Move(true, false, GameType.SINGLEPLAYER);
        vector1 = gw.getPod(0).getPosition();
        assertFalse("MoveRight", vector.x == vector1.x);
        
        vector = gw.getPod(0).getPosition();
        gm.player_Move(false ,true, GameType.SINGLEPLAYER);
        vector1 = gw.getPod(0).getPosition();
        assertFalse("MoveLeft", vector.x == vector1.x);
    }
       
}
