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
public abstract class JAMWhileBehaviour extends JAMBehaviour {
    
    /**
     * costruttore
     * @param agent agente possessore del comportament
     */
    public JAMWhileBehaviour(JAMAgent agent) {
        super(agent);
    }
    
    /**
     * run del comportamento
     */
    public void run(){
        try{
            setup();
        }catch(JAMBehaviourInterruptedException err){
            System.out.println("Errore nel setup!");
        }
        try{
            while(!isDone()){
                action();
            }
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
