/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.standalone;

import controllers.PropertiesController;

/**
 * Default values for RMI.
 * @author Etienne
 */
public class rmiDefaults {
    public static final String DEFAULT_SERVER_IP = PropertiesController.getSettings().getProperty("rmiurl");
    public static final String DEFAULT_HOST = "localhost";
    public static final String DEFAULT_PROTOCOL = "rmi";
    public static final String DEFAULT_HOSTNAME = "local";
    public static final int DEFAULT_PORT = Integer.parseInt(PropertiesController.getSettings().getProperty("rmiport"));    
}
