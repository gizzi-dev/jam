/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte_IV;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gianmarco
 */
public class ADSLImpl extends UnicastRemoteObject implements ADSL{
    private List<RemoteMessageBox> messagesBoxes;
    
    
    
    /**
     * Costruttore
     * @throws RemoteException errore remoto
     */
    public ADSLImpl() throws RemoteException{    
        messagesBoxes = new LinkedList<RemoteMessageBox>();
    }
    
    /**
     * restituisce una lista di riferimenti ad oggetti (remoti)
     * di tipo RemoteMessageBox i cui proprietarisono uguali a agentID.
     * @param agente agente possessore della RemoteMessageBox
     * @return List RemoteMessageBox
     */
    public List<RemoteMessageBox> getRemoteMessageBox(AgentID agente){ 
        List<RemoteMessageBox> box = new LinkedList<>();
        if (agente == null) {
            throw new IllegalArgumentException("Il parametro inserito è nullo");
        }         
        synchronized(this){
            for (RemoteMessageBox msg : messagesBoxes) {
                try {
                    if(((agente.getName().equals(msg.getOwner().getName() )) && (agente.getName() != "") && (agente.getCategory() != "") && (agente.getCategory().equals(msg.getOwner().getCategory())))  ||
                            (agente.getName() == "" && agente.getCategory().equals(msg.getOwner().getCategory())) ||
                            (agente.getName() == "" && agente.getCategory() == "")) {
                        box.add(msg);                        
                        System.out.println("Ritorno box "+msg.getOwner());
                    }               
                } catch (RemoteException ex) {
                    System.out.println("Errore RemoteException nella getRemoteMessageBox");
                }
            }
        }        
        return box;
    }
    
    /**
     * Richiede l'inserimento di un messageBox presso l'ADSL, se l'elemento è già presente non viene
     * effettuata alcuna operazione e viene lanciata un'opportuna eccezione
     * @param messageBox remoteMessageBox da inserire
     * @throws JAMADSLException in caso fosse già presente
     * 
     */
    public void insertRemoteMessageBox(RemoteMessageBox messageBox) throws JAMADSLException{
        if (messageBox == null) {
            throw new IllegalArgumentException("Il parametro inserito è nullo");
        }
         
        synchronized(this){
            for(RemoteMessageBox msg : messagesBoxes){
                try{
                    if(msg.getOwner().equals(messageBox.getOwner())){
                        System.out.println("MessageBox di "+messageBox.getOwner()+" gia' presente!");                        
                        throw new JAMADSLException("RemoteMessageBox gia' presente");
                    }   
                }catch (RemoteException e) {
                    System.out.println("Errore all'inserimento della Remote MessageBox");
                }               
            }
        this.messagesBoxes.add(messageBox);            
        
        }       
    } 
    
    /**
     * e richiede la cancellazione dell’oggetto RemoteMessageBox presente presso l’ADSL di propriet`a dell’agente agentID.
     * Se ’elemento non `e presente non viene effettuata alcuna operazione e viene lanciata
     * un’opportuna eccezione.
     * @param agente proprietario della remoteMessageBox
     * @throws JAMADSLException nel caso non esistesse
     */
    public void removeRemoteMessageBox(AgentID agente) throws JAMADSLException {
        if (agente == null) {
            throw new IllegalArgumentException("Il parametro inserito è nullo");
        }       
       boolean cancellato = false;
    
        synchronized(this){
            for(RemoteMessageBox msg : messagesBoxes ){
                try {
                    if (agente.equals(msg.getOwner())) {          
                        messagesBoxes.remove(msg);
                        cancellato = true;
                        System.out.println("Cancellato box "+agente.toString());                        
                    }
                } catch (RemoteException ex) {                    
                    System.out.println("Errore alla rimozione della Remote MessageBox");
                }
            }
        }
        if (!cancellato) {            
            throw new JAMADSLException("RemoteMessageBox non presente");
        }
    }
    
}
