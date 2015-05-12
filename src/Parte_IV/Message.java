
package Parte_IV;

import java.io.Serializable;

/**
 *
 * @author 746225
 */
public class Message implements Serializable{
    private AgentID sender;
    private AgentID receiver;
    private Performative performative;
    private String content;
    private Object extraArgument;//permette di memorizzare eventuali ulteriori informazioni
    
    /**
     * Primo costruttore della classe Message, nel caso non ci fossero "extraArgument"
     * @param sender il mittante del messaggio
     * @param receiver Il destinatario del messaggio
     * @param performative La performativa del messaggio
     * @param content il contenuto del messaggio 
     */
    public Message(AgentID sender,AgentID receiver,Performative performative, String content){
        this.sender=sender;
        this.receiver=receiver;
        this.performative= performative;
        this.content=content;       
    }
    
    /**
     * Secondo costruttore della classe Message, nel caso ci fossero "extraArgument".
     * @param sender il mittante del messaggio
     * @param receiver Il destinatario del messaggio
     * @param performative La performativa del messaggio
     * @param content il contenuto del messaggio 
     * @param extra argomento extra da includere nel messaggio
     */
    public Message(AgentID sender,AgentID receiver,Performative performative, String content,Object extra){
        this(sender,receiver,performative,content);
        this.extraArgument=extra;
    }
    
    /**
     * 
     * @return chi manda il messaggio 
     */
    public AgentID getSender(){
        return this.sender;
    }
    
    /**
     * Imposta chi manda il messaggio
     * @param newSender nuovo mandante del messaggio
     */
    public void setSender(AgentID newSender){
        this.sender=newSender;
    }
    
    /**
     * 
     * @return chi riceve il messaggio
     */
    public AgentID getReceiver(){
        return this.receiver;
    }
    
    /**
     * Imposta chi riceve il messaggio
     * @param newReceiver nuovo ricevitore del messaggio
     */
    public void setReceiver(AgentID newReceiver){
        this.receiver=newReceiver;
    }
    
    /**
     * 
     * @return La performativa del messaggio
     */
    public Performative getPerformative(){
        return this.performative;
    }
    
    /**
     * imposta la Performativa del messaggio
     * @see Performative
     * @param newPerformative nuova performativa del messaggio
     */
    public void setPerformative(Performative newPerformative){
        this.performative = newPerformative;
    }
    
    /**
     * 
     * @return ritorna il contenuto del messaggio
     */
    public String getContent(){
        return this.content;
    }
    
    /**
     * imposta il contenuto del messaggio
     * @param newContent nuovo contenuto del messaggio
     */
    public void setString(String newContent){
        this.content=newContent;
    }
    
    /**
     * 
     * @return il valore dell'extraArgument
     */
    public Object getExtraArg(){
        return this.extraArgument;
    }
    
    /**
     * imposta L'extraArgument
     * @param ExA il nuovo valore di extraArgument
     */
    public void setExtraArg(Object ExA){
        this.extraArgument=ExA;
    }
    
    /**
     * 
     * @return la Stringa di stampa del messaggio
     */
    public String toString(){
        String s = "Performativa: "+this.performative+"\nSender: "+this.sender+"\nReceiver: "+this.receiver+"\nContent: \n  "+this.content;
        if (this.extraArgument != null) 
            s += "\nExtra Argument: " +this.extraArgument;
        return s;
    }
    
}
