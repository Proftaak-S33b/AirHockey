/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Properties;
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
public class PropertiesControllerTest {
    PropertiesController pr;
    Properties prop;
    public PropertiesControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        prop = new Properties();
        pr = new PropertiesController(prop);
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
    public void getSettingsTest()
    {
        assertTrue("Properties", pr.getSettings().equals(prop));
    }
    
    @Test
    public void loadPropertiesTest()
    {
        assertTrue("Load props", pr.loadProperties());
    }
    
    @Test
    public void writePropertiesTest()
    {
        //weet niet wat ik hier in moet vullen
        assertTrue("Write props", pr.writeProperties(null, null, null, null, null, null, null));
        assertFalse("Write props", pr.writeProperties(null, null, null, null, null, null, null));
    }
    
    @Test
    public void isCorrectlyConfiguredTest()
    {
        assertTrue("Correct config props", pr.isCorrectlyConfigured());
    }
}
