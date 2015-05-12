/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;


import JAM.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author M746225
 */
public interface RemoteMessageBox extends Remote {
    
    
    public void writeMessage(Message message) throws RemoteException;    
    
    public PersonalAgentID getOwner() throws RemoteException;
    
}
