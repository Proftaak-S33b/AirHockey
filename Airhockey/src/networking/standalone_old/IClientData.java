package networking.standalone_old;

//<editor-fold defaultstate="collapsed" desc="imports">

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import networking.IPlayer;

//</editor-fold>


/**
 * RMI-related interface for ClientData wrapper object.
 * ClientData is used as a object to be transferred through RMI, 
 *  a wrapper for all data related to the client.
 * This makes the collecting and communicating data easier and shorter.
 * @author Etienne
 */
public interface IClientData extends Remote{
    
    /**
     * Returns the ipaddress of the server.
     * @return the address as InetAdress.
     */
    public InetAddress getAddress();

    /**
     * Returns the name of the server.
     * @return the name as a String.
     */
    public String getName();

    /**
     * Returns the title of the lobby.
     * @return the description as a String.
     */
    public String getDescription();

    /**
     * Returns the socket of the 'clientclient';
     *  the clientside of the user's application.
     * (often commonly <i>and confusingly</i> referred to as client).
     * @return the socket as a Socket object.
     */
    public Socket getSocket();

    /**
     * Returns the socket of the 'clientserver';
     *  the serverside of the user's application.
     * (often commonly <i>and confusingly</i> referred to as client).
     * @return the serversocket as a ServerSocket object.
     */
    public ServerSocket getServersocket();

    /**
     * Returns the player who tries to go online.
     * @return the host as an IPlayer interface.
     */
    public IPlayer getHost();
    
    /**
     * 
     * @return 
     */
    public int getRanking();
    
    /**
     * 
     * @return 
     */
    public int getPlayerAmount();
    
    /**
     * Sets the player count to the given amount
     * @param playerCount The new player count
     */
    public void setPlayerAmount(int playerCount);
}
