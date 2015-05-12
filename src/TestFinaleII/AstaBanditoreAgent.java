/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM.TestFinaleII;

import JAM.*;
/**
 *
 * @author Gianmarco
 */
public class AstaBanditoreAgent extends JAMAgent{
    private boolean asta;
    
     public AstaBanditoreAgent(String name, String category) throws JAMADSLException {
        super(new PersonalAgentID(name, category));
        addBehaviour(new TerminaAstaAgent(this));
        addBehaviour(new AstaBanditoreBehaviour(this));
        JAMAgentMonitor banditore = new JAMAgentMonitor(this);
        this.init();
        this.start();
        
    }
     
     public static void main(String[] args) throws JAMADSLException {
        AstaBanditoreAgent astabanditoreagent = new AstaBanditoreAgent("Ciccio", "Banditore");              
    }
     
     public boolean getAsta(){
         return asta;
     }
     
     public void setAsta(boolean asta){
         this.asta=asta;
     }
     
     
     
     
        
}
    

