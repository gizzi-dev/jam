/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M746225
 */
public abstract class JAMAgent extends Observable {
    
    private MessageBox myMessageBox;
    private PersonalAgentID myID;
    private ADSL adsl;
    private String name;
    private String ip;
    private int port;
    private String log; 
    private LinkedList<JAMBehaviour> myBehaviours;
    
    /**
     * 
     * @param myID nome dell'agente
     */
    public JAMAgent(AgentID myID){
        this(myID,"127.0.0.1", "adsl", 1099);
    }
    
    /**
     * 
     * @param myID nome dell'agente
     * @param ip contiene l'indirizzo IP dello rmiregistry
     * @param name contenente l'indirizzo dell'adsl nello rmiregistry
     * @param port contiene il numero della porta su cui è disponibile lo rmi
     *  registry all'indirizzo <i>ip</i>
     */
    public JAMAgent(AgentID myID,String ip,String name,int port) {
        this.myID = (PersonalAgentID) myID;
        this.ip=ip;
        this.name = name;
        this.port = port;
        try {
            this.myMessageBox = new MessageBox(this.myID);
        } catch (RemoteException ex) {
            System.out.println(ex);            
        } 
        myBehaviours = new LinkedList<JAMBehaviour>();        
    }
    
    /**
     * Effettua la lookup presso lo rmiregistry all'indirizzo ip/port dell'oggetto ADSL
     * di nome ADSL e memorizza tale riferimento remoto in adsl, quindi se tutto è andato bene,
     * iscrive presso l'ADSL l'oggetto di tipo MessageBox myMessageBox
     * @throws JAMADSLException  in caso di errore
     */
    public void init() throws JAMADSLException {        
        try {
            /* Accedo al registry*/
            adsl = (ADSL) Naming.lookup("//" + ip + ":" + port + "/" + name);
            adsl.insertRemoteMessageBox(myMessageBox);
            log = "init Eseguta presso //" + ip + ":" + port + "/" + name+"\n"+myID;
            setChanged();
            notifyObservers(log);
            List<RemoteMessageBox> box = adsl.getRemoteMessageBox(myID);
            
        }
        catch (NotBoundException | MalformedURLException | RemoteException ex) {           
            log = "Errore Init!";
            setChanged();
            notifyObservers(log); 
        }
    }
    
    /**
     * Effettua la rimozione della casella myMessageBox dall'ADSL
     * @throws JAMADSLException in caso non ci fosse l'adsl da distruggere
     */
    public void destroy() throws JAMADSLException{
    try{
        try {
            adsl.removeRemoteMessageBox(myID);
            log = "destroy Eseguta!";
            setChanged();
            notifyObservers(log);
            /*for(JAMBehaviour behaviour: myBehaviours)
                behaviour.done();     */               
            } catch (NullPointerException e) {
                System.out.println("Errore - destroy() - myID non presente nell'adsl");
                log = "Errore destroy - devi prima effettuare init!";
                setChanged();
                notifyObservers(log);
            }
        }
        catch(RemoteException ex) {
            log = "Errore destroy - devi avviare l'ADSL";
            setChanged();
            notifyObservers(log);
            throw new JAMADSLException(ex.getMessage(), ex);
        }
    }
    
    /**
     * restituisce true se vi sono messaggi in myMessageBox, false altrimenti
     * @return boolean
     */
    public boolean check(){
        return this.myMessageBox.isThereMessage();        
    }
    
    /**
     * restituisce true se vi sono messaggi in myMessageBox con una certa performativa, false altrimenti
     * @param performative performativa del messaggio da ricercare
     * @return boolean
     */
    public boolean check(Performative performative){
        return this.myMessageBox.isThereMessage(performative);        
    }
    
    /**
     *  restituisce true se vi sono messaggi in myMessageBox dell'AgentID agente, false altrimenti
     * @param agente agente del messaggio da ricercare
     * @return boolean
     */
    public boolean check(AgentID agente){
        return this.myMessageBox.isThereMessage(agente);        
    }
    
    /**
     * restituisce true se vi sono messaggi in myMessageBox dell'AgentID agente con una
     * certa performativa, false altrimenti
     * @param agente agente del messaggio da ricercare
     * @param performative performativa del messaggio da ricercare
     * @return boolean
     */
    public boolean check(AgentID agente,Performative performative){
        return this.myMessageBox.isThereMessage(agente,performative);        
    }
    
    /**
     * Richiede all'ADSL mediante il metodo getRemoteMessageBox gli oggetti ti tipo RemoteMessageBox
     * il cui proprietario è specificato dal receiver del messaggio, su tali oggetti invoca la writeMessage
     * di message
     * @param message il messaggio da inviare
     * @throws JAMADSLException in caso errore sull'adsl
     */
    public void send(Message message) throws JAMADSLException{       
        List<RemoteMessageBox> box= null;
        try {            
            box = adsl.getRemoteMessageBox(message.getReceiver());            
        } catch (RemoteException ex) {
            System.out.println("Error: "+ex);
        }
            for(RemoteMessageBox dest: box ){                
                try {                    
                    dest.writeMessage(message);
                    log = "-->SEND message "+message.getPerformative()+" to "+message.getReceiver()+"\n"+message;
                    setChanged();
                    notifyObservers(log);                                       
                } catch (RemoteException ex) {                    
                    String log = "Errore nell'invio del messaggio remoto(send di JAMAgent)";
                    setChanged();
                    notifyObservers(log); 
                } 
            }
    }
    
    /**
     * Legge dalla propria casella myMessageBox un messaggio tramite il metodo readMessage() 
     * con le caratteristiche specificate dai parametri
     * @return Message 
     */
    public Message receive() {
        Message msg = null;
        try {
            msg = this.myMessageBox.readMessage();
            log = "-->RECEIVE message "+msg.getPerformative()+" from "+msg.getSender()+"\n"+msg;
            setChanged();
            notifyObservers(log);    
        } catch (JAMMessageBoxException ex) {
            System.out.println("Messaggio non presente "+ex);
        }                
        return msg;
    }
    
    /**
     * Legge dalla propria casella myMessageBox un messaggio tramite il metodo readMessage() 
     * con le caratteristiche specificate dai parametri
     * @param performative performativa del messaggio da ricevere
     * @return Message
     */
    public Message receive(Performative performative){
        Message msg = null;
        try {
            msg = this.myMessageBox.readMessage(performative);
            log = "-->RECEIVE message "+msg.getPerformative()+" from "+msg.getSender()+"\n"+msg;
            setChanged();
            notifyObservers(log);     
        } catch (JAMMessageBoxException ex) {
            System.out.println("Messaggio non presente "+ex);        
        }           
        return msg;
    }
    
    /**
     * Legge dalla propria casella myMessageBox un messaggio tramite il metodo readMessage() 
     * con le caratteristiche specificate dai parametri
     * @param agente agente del messaggio da ricevere
     * @return Message
     */
    public Message receive(AgentID agente) {
        Message msg = null;
        try {
            msg = this.myMessageBox.readMessage(agente);
            log = "-->RECEIVE message "+msg.getPerformative()+" from "+msg.getSender()+"\n"+msg;
            setChanged();
            notifyObservers(log); 
        } catch (JAMMessageBoxException ex) {
            System.out.println("Messaggio non presente "+ex);
        }               
        return msg;
    }
    
    /**
     * Legge dalla propria casella myMessageBox un messaggio tramite il metodo readMessage() 
     * con le caratteristiche specificate dai parametri
     * @param agente agente del messaggio da ricevere
     * @param performative performativa del messaggio da ricevere
     * @return Message
     */
    public Message receive(AgentID agente,Performative performative){
        Message msg = null;
        try {
            msg = this.myMessageBox.readMessage(agente, performative);
            log = "-->RECEIVE message "+msg.getPerformative()+" from "+msg.getSender()+"\n"+msg;
            setChanged();
            notifyObservers(log);     
        } catch (JAMMessageBoxException ex) {
            System.out.println("Messaggio non presente "+ex);
        }           
        return msg;
    }
    
    /**
     * 
     * @return PersonalAgentID
     */
    public PersonalAgentID getMyID() {
        return this.myID;
    }
    
    
    public void start() {
        for(JAMBehaviour behaviour: myBehaviours){
            Thread newThread = new Thread(behaviour);
            newThread.start();
            log = "Start di "+newThread.getName();
            setChanged();
            notifyObservers(log);     
        }
    }
    
    public void addBehaviour(JAMBehaviour behaviour){
        myBehaviours.add(behaviour);
    }
}
