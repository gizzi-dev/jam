/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM.TestFinaleII;
import JAM.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gianmarco
 */
public class TerminaAstaAgent extends JAMSimpleBehaviour{
    private int tempo;
    private AstaBanditoreAgent a;
    
      public TerminaAstaAgent(JAMAgent myAgent) {
        super(myAgent);
    }
    
    public void setup() throws JAMBehaviourInterruptedException{
        tempo = 20000;//millisecondi     
        a = (AstaBanditoreAgent) myAgent;
        System.out.println("L'asta durera' : "+tempo/1000+" sec");
    } 
    
    public void dispose() throws JAMBehaviourInterruptedException{
       a.setAsta(false);
        
    }
    
    public void action() throws JAMBehaviourInterruptedException{
        sleep(tempo);
            
        
    }
      
}
