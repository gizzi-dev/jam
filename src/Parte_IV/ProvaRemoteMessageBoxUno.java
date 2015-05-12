/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte_IV;

import java.rmi.Naming;

/**
 *
 * @author Gianmarco
 */
public class ProvaRemoteMessageBoxUno {
    public static void main(String[] args){
        
        ADSL adsl;
        PersonalAgentID agente1 = new PersonalAgentID("Pino", "A");
        System.out.println("Ciao, sono " + agente1);
        PersonalAgentID agente2 = new PersonalAgentID("Gino","A");
        System.out.println("Ciao, sono " + agente2);
        PersonalAgentID agente3 = new PersonalAgentID("Topolino","B");
        System.out.println("Ciao, sono " + agente3);
        try{
            MessageBox box1 = new MessageBox(agente1);
            MessageBox box2 = new MessageBox(agente2);
            MessageBox box3 = new MessageBox(agente1);
            MessageBox box4 = new MessageBox(agente3);        
            System.out.println("Mi connetto all'adsl");
            adsl = (ADSL)Naming.lookup("rmi://localhost:2000/ADSL");
            System.out.println("Connesso!!");            
            adsl.insertRemoteMessageBox(box1);
            adsl.insertRemoteMessageBox(box2);
           // adsl.insertRemoteMessageBox(box3);
            adsl.insertRemoteMessageBox(box4);
            System.out.println("Ho inserito le Boxs");
            //List<RemoteMessageBox> boxes = adsl.getRemoteMessageBox(agente3);
            CategoryAgentID agenteC = new CategoryAgentID("A");
            GenericAgentID agenteG = new GenericAgentID();
            //adsl.removeRemoteMessageBox(agente3);
            adsl.getRemoteMessageBox(agenteG);
        }
        catch(Exception e){
            System.out.println("RMI Fallita.\n"+e);
            
        }
    }
}
