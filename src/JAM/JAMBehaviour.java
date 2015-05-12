/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M746225
 */
public abstract class JAMBehaviour implements Runnable {
    private boolean done; //indica se il comportamento è stato eseguito completamente
    private Thread myThread;//il riferimento all'effettivo thread che esegue il comportamento
    public JAMAgent myAgent;//indica l'agente possessore di tale comportamento
   
    /**
     * Costruttore
     * @param agent JAMAgent
     */
    public JAMBehaviour(JAMAgent agent){
        this.myAgent = agent;
        this.done = false;
        this.myThread=Thread.currentThread();        
    }
    
    /**
     * imposta done a true this.myThread.interrupt(); nel caso il thread in questione si in stato di wait 
     * e quindi non può accorgersi della terminazione
     * interrupt permette di uscire dallo stato di wait
     */    
    public void done(){
        if (isAvviabile()) {
            throw new IllegalStateException("Il thread del comportamento che si vuole terminare non è mai stato avviato.");
        }
        this.done = true;
        this.myThread.interrupt();
    }
    
    /**
     * Controlla se il comportamento è avviabile o meno
     * @return boolean
     */
    public boolean isAvviabile(){
    return this.myThread == null;
    }
    
   
    /**
     * restituisce il valore corrente della variabile booleana done
     * @return boolean
     */
    public boolean isDone(){
        return this.done;
    }
    
   
    /**
     * imposta il valore della variabile myThread
     * @param myThread modifica la variabile myThread
     */
    public void setMyThread(Thread myThread){
        this.myThread = myThread;
    }
    
    
    /**
     * invoca la Thread.sleep sul thread corrente per ms millisecondi
     * @param ms millisecondi
     */
    public void sleep(long ms){
        try {        
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
           System.out.println("Errore Durante la sleep!");
        }
    }
    
    
    /**
     * metodo astratto che definisce il codice da eseguire una o più volte;
     * @throws JAMBehaviourInterruptedException in caso di interruzione
     */
    public abstract void action() throws JAMBehaviourInterruptedException;
    
    
    /**
     * metodo astratto che definisce il codice da eseguire prima di lanciare il metodo action
     * @throws JAMBehaviourInterruptedException in caso di interruzione
     */
    public abstract void setup() throws JAMBehaviourInterruptedException;
    
    
    /**
     * metodo astratto che definisce il codice da eseguire prima di terminare l'esecuzione del comportamento
     * @throws JAMBehaviourInterruptedException in caso di interruzione
     */
    public abstract void dispose() throws JAMBehaviourInterruptedException;
    
}
