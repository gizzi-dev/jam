/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JAM;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


/**
 *
 * @author M746225
 */
public class MessageBoxNoSync_1 extends UnicastRemoteObject{
    private PersonalAgentID owner;    
    private List<Message> box;
    
    /**
     * Costruttore della classe MessageBoxNoSync
     * @param owner l'agente possessore della lista di messaggi
     * @throws RemoteException in caso di errore remoto
     */
    public MessageBoxNoSync_1(PersonalAgentID owner) throws RemoteException{
        this.owner = owner;
        box = new LinkedList<Message>();
    }
    
    /**
     * 
     * @return il possessore della box
     */
    public PersonalAgentID getOwner(){
        return this.owner;
    }
    
    /**
     * 
     * @return true o false se la box è piena o vuota
     */
    public boolean isBoxEmpty(){
        return this.box.isEmpty();
    }
    
    /**
    * Rtirona il primo messaggio nella box
    * @return Message
    * @throws JAMMessageBoxException  in caso non si siano messaggi
    */
    public Message readMessage() throws JAMMessageBoxException{
        if(!(this.isBoxEmpty()))
            return this.box.remove(0);
        throw new JAMMessageBoxException("Nessun messaggio");
    }
    
    /**
     * lettura del primo messaggio in coda inviato da un certo agente;
     * @param agente mandante del messaggio
     * @return Message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public Message readMessage(AgentID agente) throws JAMMessageBoxException{
        for (Message msg : box) {
            if (agente.equals(msg.getSender()) ) {
                box.remove(msg); 
                return msg;
            }
        }
       throw new JAMMessageBoxException("Nessun messaggio dell'agente "+agente);
    }
    
    /**
     * lettura del primo messaggio in coda corrispondente ad una certa performativa;
     * @param performative performativa del messaggio
     * @return Message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public Message readMessage(Performative performative ) throws JAMMessageBoxException{
        for (Message msg : box) {
            if (performative.equals(msg.getPerformative()) ) {
                box.remove(msg); 
                return msg;
            }
        }
        throw new JAMMessageBoxException("Nessun messaggio con performativa "+performative);
    }
    
    /**
     * Legge il messaggio di un determinato agente con una determinata performativa
     * @param agente mandante del messaggio
     * @param performative performativa del messaggio
     * @return message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public Message readMessage(AgentID agente,Performative performative ) throws JAMMessageBoxException{
        for (Message msg : box) {
            if (performative.equals(msg.getPerformative()) && agente.equals(msg.getSender()) ) {
                box.remove(msg); 
                return msg;
            }
        }
        throw new JAMMessageBoxException("Nessun messaggio dell'agente "+agente+" con performativa "+performative);
    }
    
    /**
     * restituisce true se `e presente un messaggio in coda su box con di AgentID agente
     * e performative performative
     * @param agente agente mandante del messaggio
     * @param performative performativa del messaggio
     * @return boolean
     */
    public boolean isThereMessage(AgentID agente, Performative performative ){
        for (Message msg : box) 
            if (performative.equals(msg.getPerformative())&& agente.equals(msg.getSender()) ) 
                return true;
        return false;
    }
    
    /**
     * restituisce true se `e presente un messaggio in coda su box con di AgentID agente     * 
     * @param agente agente mandante del messaggio 
     * @return boolean
     */
    public boolean isThereMessage(AgentID agente ){
        for (Message msg : box) 
            if (agente.equals(msg.getSender()) ) 
                return true;
        return false;
    }
    
    /**
     * restituisce true se è presente un messaggio in coda su box con performative performative
     * @param performative performativa del messaggio
     * @return boolean
     */
    public boolean isThereMessage(Performative performative ){
        for (Message msg : box) 
            if (performative.equals(msg.getPerformative()) ) 
                return true;
        return false;
    }
    
    /**
     * Se c'è o meno un messaggio nel box
     * @return boolean
     */
    public boolean isThereMessage( ){
        return !box.isEmpty();
    }
    
    /**
     * Aggiunge il message alla box
     * @param msg il messaggio da inviare
     */
    public void writeMessage(Message msg){
        this.box.add(msg);
    }
   
    
    
}
