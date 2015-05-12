/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

import java.rmi.RemoteException;
import java.util.ConcurrentModificationException;

/**
 *
 * @author M746225
 */
public class MessageBox extends MessageBoxNoSync implements RemoteMessageBox{
    
    /**
     * Costruttore
     * @param owner il possessore della MessageBox
     * @throws RemoteException in caso di errore remoto
     */
    public MessageBox(PersonalAgentID owner) throws RemoteException {
        super(owner);
    }
    
    /**
     * Legge un messaggio dal box, se non ce ne sono si mette in wait
     * @return Message 
     * @throws JAMMessageBoxException nel caso il msg non sia presente
     */
    synchronized public Message readMessage() throws JAMMessageBoxException{
        while (!isThereMessage()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Message msg = super.readMessage();
        notifyAll();
        return msg;
    }        
    
    /**
     * Legge un messaggio dell'agente Agente, se non ce ne sono si mette in wait
     * @param agente l'agente del messaggio da leggere
     * @return Message
     * @throws JAMMessageBoxException nel caso il msg non sia presente
     */
    synchronized public Message readMessage(AgentID agente) throws JAMMessageBoxException{
        while (!isThereMessage(agente)) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Message msg = super.readMessage(agente);
        notifyAll();
        return msg;
    }
    
    /**
     * Legge un messaggio con una certa <i>Performativa</i>, se non ce ne sono si mette in wait
     * @param performative performativa del messaggio da leggere
     * @return Message
     * @throws JAMMessageBoxException nel caso il msg non sia presente
     */
    synchronized public Message readMessage(Performative performative) throws JAMMessageBoxException{
        while (!isThereMessage()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Message msg = super.readMessage(performative);
        notifyAll();
        return msg;
    }
    
    /**
     * Legge un messaggio dell'agente Agente con una certa performativa, se non ce ne sono si mette in wait
     * @param agente agente del messaggio da leggere
     * @param performative performativa del messaggio da leggere
     * @return Message
     * @throws JAMMessageBoxException nel caso il msg non sia presente
     */
    synchronized public Message readMessage(AgentID agente,Performative performative) throws JAMMessageBoxException{
        while (!isThereMessage()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Message msg = super.readMessage(agente,performative);
        notifyAll();
        return msg;
    }
    
    /**
     * Controlla se ci sono messaggi nella box
     * @return boolean
     */
    public boolean isThereMessage() {
        try{
            return super.isThereMessage();
        }catch(ConcurrentModificationException e){
            System.out.println("La coda è stata modificata in questo momento");
        }       
        return false;
    }
    
    /**
     * Controlla se ci sono messaggi dell'agente agente nella box
     * @param agente agente del messaggio
     * @return boolean 
     */
    public boolean isThereMessage(AgentID agente) {
       try{
            return super.isThereMessage(agente);
        }catch(ConcurrentModificationException e){
            System.out.println("La coda è stata modificata in questo momento");
        }       
        return false;
    }
    
    /**
     * Controlla se ci sono messaggi con una certa performativa nella box
     * @param performative performativa del messaggio
     * @return boolean
     */
    public boolean isThereMessage(Performative performative) {
        try{
            return super.isThereMessage(performative);
        }catch(ConcurrentModificationException e){
            System.out.println("La coda è stata modificata in questo momento");
        }       
        return false;
    }
    
    /**
     * Controlla se ci sono messaggi dell'agente agente ed una certa performativa nella box
     * @param agente agente del messaggio
     * @param performative performativa del messaggio 
     * @return boolean
     */
    public boolean isThereMessage(AgentID agente, Performative performative) {
        try{
            return super.isThereMessage(agente,performative);
        }catch(ConcurrentModificationException e){
            System.out.println("La coda è stata modificata in questo momento");
        }       
        return false;
    }
    
    /**
     * Scrive un messaggio nella box
     * @param msg il messaggio da inviare
     */
    synchronized public void writeMessage(Message msg){
        super.writeMessage(msg);
        notifyAll(); 
    }
}
