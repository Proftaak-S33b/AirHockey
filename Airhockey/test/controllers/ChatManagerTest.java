/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.Human;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.IPlayer;
import networking.Message;
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
public class ChatManagerTest {
    ChatManager cm;
    private ObservableList<IPlayer> players;
    private ObservableList<Message> messages;
    public ChatManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        cm = new ChatManager();
        players = FXCollections.observableArrayList();
        players.add(new Human("ik","w8woord", 20));
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
    public void addMessageTest()
    {
        assertTrue("Message with String and Player", cm.addMessage("fiets", players.get(0)));
        assertFalse("Message without String", cm.addMessage(null, players.get(0)));
        assertFalse("Message with empty String", cm.addMessage("", players.get(0)));
        assertFalse("Message without String", cm.addMessage("fiets", null));
    }
    
    @Test
    public void getMessagesTest()
    {
        cm.addMessage("fiets", players.get(0));
        messages = cm.getMessages();
        assertTrue("Received message", messages.get(0).getText().equals("fiets"));
    }
}
