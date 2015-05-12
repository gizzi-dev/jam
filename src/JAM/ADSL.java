/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * L'ADSL ha lo scopo di pubblicare i RemoteMessageBox degli agente presenti nella piattaforma in un dato momento.
 * 
 * @author M746225
 */
public interface ADSL extends Remote {
    
    
    public List<RemoteMessageBox> getRemoteMessageBox(AgentID agente) throws RemoteException;
    
    public void insertRemoteMessageBox(RemoteMessageBox messageBox) throws RemoteException,JAMADSLException;
    
    public void removeRemoteMessageBox(AgentID agente)throws RemoteException,JAMADSLException;
}
