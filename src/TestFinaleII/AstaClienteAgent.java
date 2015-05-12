/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM.TestFinaleII;
import JAM.*;
/**
 *
 * @author M746225
 */
public class AstaClienteAgent extends JAMAgent{
    
    
     public AstaClienteAgent(String category, String name) throws JAMADSLException {
        super(new PersonalAgentID(category, name));
        addBehaviour(new AstaClienteBehaviour(this));     
        JAMAgentMonitor cliente = new JAMAgentMonitor(this);
        this.init();
        this.start();
        
    }
     
     public static void main(String[] args) throws JAMADSLException {
        AstaClienteAgent astaclienteagent = new AstaClienteAgent("Ciccio", "Cliente");
        AstaClienteAgent astaclienteagent2 = new AstaClienteAgent("Giovanni", "Cliente");
        AstaClienteAgent astaclienteagent3 = new AstaClienteAgent("Pasquale", "Cliente");
        
        
     }
     
     
        
}
