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
    public static String DEFAULT_SERVER_IP() {return PropertiesController.getSettings().getProperty("rmiurl");};
    public static String DEFAULT_HOST = "localhost";
    public static String DEFAULT_PROTOCOL = "rmi";
    public static int DEFAULT_PORT() { return Integer.parseInt(PropertiesController.getSettings().getProperty("rmiport"));};    
}
