/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.*;

/**
 * Static class for the database connection.
 * @author Jur
 */
public final class DatabaseManager {
    public static Connection connection;
    
    /**
     * Opens the connection to the database
     * @return Returns true if succesfull,
     * else returns false
     */
    private static boolean openConnection()
    {
        boolean result;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://a-chan.nl:3306/deb82648_pts", "deb82648_air", "airhockey");
            result = true;
        }
        catch(Exception e){
            connection = null;
            System.out.println(e.getMessage());
            System.out.println("Connection failed");
            result = false;
        }
        return result;
    }
    
    /**
     * Closes the database connection
     */
    private static void closeConnection(){
        try{
            connection.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Checks if the username and password the user entered are correct.
     * @param username The username of the user
     * @param password The password of the user
     * @return Returns a boolean if the password and username match or not
     */
    public static boolean authenticateUser(String username, String password){
        boolean result = false;
        //Open connection
        if(openConnection()){
            try{
                //Try to execute sql statment
                Statement stmnt = connection.createStatement();
                String SQL = "SELECT playerName, playerPassword FROM PLAYER WHERE playerName = '";
                SQL += username+ "'" + " AND playerPassword = " + "'" + password + "'" + ";";
                ResultSet rs = stmnt.executeQuery(SQL);
                //Check if password and username match
                if(rs.next())
                {
                    result = true;
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return result;
    }
    
    /**
     * Creates a new user with the given variables
     * @param username Username for the user
     * @param password Password for the user
     * @return Boolean. True if succesfull, false if not
     */
    public static boolean addUser(String username, String password){
        boolean result = false;
        //Open the connection
        if(openConnection() && !username.trim().isEmpty() && !password.trim().isEmpty()){
            try{
                //Try to execute sql statment
                Statement stmnt = connection.createStatement();
                String SQL = "INSERT INTO PLAYER VALUES(";
                SQL += "'" + username + "'";
                SQL += ",'" + password + "'";
                SQL += ", 0);";
                if(stmnt.executeUpdate(SQL) > 0){
                    result = true;
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        //Close connection
        closeConnection();
        return result;
    }
}

