package networking.sockets_old;

import java.io.Serializable;

/**
 * Class that remembers the client data
 * @author Jur
 */
public class ClientData implements Serializable{
    private final int nr;
    private final String ipAdress;
    
    /**
     * Creates a new clientData object that can be used for host migration
     * @param ip The ipadress of the client
     */
    public ClientData(String ip){
        Data.clientNr++;
        nr = Data.clientNr;
        
        this.ipAdress = ip;
    }
    
    /**
     * Gets the ipadress from the client
     * @return The ipadress
     */
    public String getIpadress(){
        return this.ipAdress;
    }
}