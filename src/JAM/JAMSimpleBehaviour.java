/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;


/**
 *
 * @author M746225
 */
public abstract class JAMSimpleBehaviour extends JAMBehaviour {
    
    /**
     * costruttore
     * @param agent agente possessore del comportamento
     */
    public JAMSimpleBehaviour(JAMAgent agent) {
        super(agent);
    }
    
    /**
     * la run del comportamento
     */
    public void run(){
        try{
            setup();
        }catch(JAMBehaviourInterruptedException err){
            System.out.println("Errore nel setup!");
        }
        try{
            action();
        } catch(JAMBehaviourInterruptedException err){
            if(isDone())  return; //controlla re l'interrupt è arrivato da done()
            System.out.println("Agente interrotto");
        } finally{
            try{
                dispose();
            } catch(JAMBehaviourInterruptedException err){
                System.out.println("Errore durante il dispose");
            }
        }
    }
    
}
