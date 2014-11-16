/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Joris
 */
public class PropertiesController {

    private final Properties props;

    /**
     * Instantiates a new SettingsController that can write and read the
     * properties file from the root folder of the application
     *
     * @param props Can be null, the properties this controller will use.
     */
    public PropertiesController(Properties props) {
        if (props != null) {
            this.props = props;
        } else {
            this.props = new Properties();
        }
    }

    /**
     * Gets the Properties from this PropertiesController
     *
     * @return The properties file this controller manages
     */
    public Properties getSettings() {
        return props;
    }

    /**
     * Loads the properties file from the root folder of the applications
     *
     * @return True if success, false if failed
     */
    public boolean loadProperties() {
        InputStream input = null;
        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            props.load(input);

            if (!isCorrectlyConfigured()) {
                return false;
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Writes the properties file at the root folder of the application with
     * speciefied parameters. All parameters must have a value.
     *
     * @param rmiUrl
     * @param rmiPort
     * @param rmiRegistry
     * @param dbUrl
     * @param dbPort
     * @param dbUsername
     * @param dbPassword
     * @return
     */
    public boolean writeProperties(String rmiUrl, String rmiPort, String rmiRegistry,
            String dbUrl, String dbPort, String dbUsername, String dbPassword) {

        //TODO -> Check if all properties have a proper value
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            //Set RMI server properties
            props.setProperty("rmiurl", rmiUrl);
            props.setProperty("rmiport", rmiPort);
            props.setProperty("rmiregistry", rmiRegistry);

            //Set database properties
            props.setProperty("dburl", dbUrl);
            props.setProperty("dbport", dbPort);
            props.setProperty("dbusername", dbUsername);
            props.setProperty("dbpassword", dbPassword);

            if (!isCorrectlyConfigured()) {
                return false;
            }
            // save properties to project root folder
            props.store(output, null);

        } catch (IOException io) {
            System.out.println(io.getMessage());
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the properties file is correctly configured
     *
     * @return False if not correctly configured, otherwise true.
     */
    public boolean isCorrectlyConfigured() {
        if (props == null) {
            return false;
        }
        if (!props.containsKey("rmiurl")) {
            return false;
        }
        if (!props.containsKey("rmiport")) {
            return false;
        }
        if (!props.containsKey("rmiregistry")) {
            return false;
        }
        if (!props.containsKey("dburl")) {
            return false;
        }
        if (!props.containsKey("dbport")) {
            return false;
        }
        if (!props.containsKey("dbusername")) {
            return false;
        }
        if (!props.containsKey("dbpassword")) {
            return false;
        }
        return true;
    }
}
