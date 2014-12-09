/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
public class DatabaseManagerTest {
    DatabaseManager dm;
    public DatabaseManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dm = new DatabaseManager();
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
    public void authenticateUserTest()
    {
        assertTrue("User Bob1 Inloggen", DatabaseManager.authenticateUser("Bob1", "1234"));
        assertFalse("User Piet Inloggen", DatabaseManager.authenticateUser("Piet", "1234"));
        assertFalse("User without name", DatabaseManager.authenticateUser("", "1234"));
        assertFalse("User without password", DatabaseManager.authenticateUser("Piet", ""));
    }
    
    @Test
    public void addUserTest()
    {
        assertTrue("User Bob61 add", DatabaseManager.addUser("Bob61", "1234"));
        assertFalse("User Piet Inloggen", DatabaseManager.addUser("Bob1", "1234"));
        assertFalse("User without name", DatabaseManager.addUser("", "1234"));
        assertFalse("User without password", DatabaseManager.addUser("Piet1", ""));
    }
}
