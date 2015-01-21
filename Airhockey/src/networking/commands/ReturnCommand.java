/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.commands;

import networking.standalone.Connection;

/**
 *
 * @author Joris
 */
public interface ReturnCommand extends Command {

    /**
     * Only use server-side please! Sets the return address for the return value
     * of this command.
     *
     * @param conn the connection to return the result to.
     */
    public void setReturnAddress(Connection conn);
}
