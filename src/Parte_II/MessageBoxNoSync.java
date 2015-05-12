/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Parte_II;

import Parte_I.*;
import java.util.*;


/**
 *
 * @author M746225
 */
public class MessageBoxNoSync {
    private PersonalAgentID owner;    
    private List<Message> box;
    
    /**
     * Costruttore della classe MessageBoxNoSync
     * @param owner l'agente possessore della lista di messaggi
     */
    public MessageBoxNoSync(PersonalAgentID owner) {
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
     * Legge il messaggio di un determinato agente con una determinata 
     * @return message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public Message readMessage() throws JAMMessageBoxException{
        if(!(this.isBoxEmpty()))
            return this.box.remove(0);
        throw new JAMMessageBoxException("Nessun messaggio");
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
     * Legge il messaggio di un determinato agente con una determinata performativa
     * @param agente mandante del messaggio
     * @param performative performativa del messaggio
     * @return message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public boolean isThereMessage(AgentID agente, Performative performative ) throws JAMMessageBoxException{
        for (Message msg : box) 
            if (performative.equals(msg.getPerformative())&& agente.equals(msg.getSender()) ) 
                return true;
        return false;
    }
    
   /**
     * Legge il messaggio di un determinato agente 
     * @param agente mandante del messaggio     
     * @return message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public boolean isThereMessage(AgentID agente ) throws JAMMessageBoxException{
        for (Message msg : box) 
            if (agente.equals(msg.getSender()) ) 
                return true;
        return false;
    }
    
    /**
     * Legge il messaggio  con una determinata performativa
     * @param performative performativa del messaggio
     * @return message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public boolean isThereMessage(Performative performative ) throws JAMMessageBoxException{
        for (Message msg : box) 
            if (performative.equals(msg.getPerformative()) ) 
                return true;
        return false;
    }
    
   /**
     * Legge il primo messaggio
     * @return message
     * @throws JAMMessageBoxException  in caso non si siano messaggi
     */
    public boolean isThereMessage( ) throws JAMMessageBoxException{
        for (Message msg : box){
            return true;
        }
        return false;
    }
    
    /**
     * Aggiunge il message alla box
     * @param message msg da inserire
     */
    public void writeMessage(Message message){
        this.box.add(message);
    }
    
    
}
