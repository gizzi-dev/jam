/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

import Parte_II.*;
import Parte_I.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gianmarco
 */
public class TestParteII {
    
    public static void main(String[] args){
        
        PersonalAgentID uno = new PersonalAgentID("topolino", "categoria1");
        PersonalAgentID due = new PersonalAgentID("pippo", "categoria2");
        PersonalAgentID tre = new PersonalAgentID("paperino", "categoria1");
        
        
        //isBoxEmpty Test
        MessageBoxNoSync box = null;
        try {
            box = new MessageBoxNoSync(due);
        } catch (RemoteException ex) {
            Logger.getLogger(TestParteII.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("La box di "+due.getName() +" e' vuota(true)? "+box.isBoxEmpty() );
        
        Message messaggio1 = new Message(uno, due,Performative.REQUEST,"come stai?");       
        Message messaggio2 = new Message(tre,due,Performative.AGREE,"si" );
        Message messaggio3 = new Message(tre,due,Performative.AGREE,"no","ciao" );
        
        //Aggiungo messaggi al box
        box.writeMessage(messaggio1);
        box.writeMessage(messaggio2);//questo non dovrebbe essere aggiunto in quando due non è il ricevente
        box.writeMessage(messaggio3);        
        //testo se li ha aggiunti
        System.out.println("La box di "+due.getName() +" e' vuota(false)? "+box.isBoxEmpty());
        //isThereMessage Test
         try {
        System.out.println("Ci sono messaggi(true)? "+box.isThereMessage());
        System.out.println("Ci sono messaggi INFORM(false)? "+box.isThereMessage(Performative.INFORM));
        System.out.println("Ci sono messaggi REQUEST(true)? "+box.isThereMessage(Performative.REQUEST));
        
            System.out.println("Ci sono messaggi dell'agente uno(true)? "+box.isThereMessage(uno));        
            System.out.println("Ci sono messaggi dell'agente due(false)? "+box.isThereMessage(due));
            System.out.println("Ci sono messaggi dell'agente uno di tipo REQUEST(true)?\n"+box.isThereMessage(uno, Performative.REQUEST));
            System.out.println("Ci sono messaggi dell'agente uno di tipo AGREE(false)? "+box.isThereMessage(uno, Performative.AGREE));
            //readMessage Test
            Message messaggio5 = new Message(tre,due,Performative.REQUEST,"Come va?");
            box.writeMessage(messaggio5);
            System.out.println("Il primo messaggio di tipo REQUEST? "+ box.readMessage(Performative.REQUEST));
            System.out.println("Qual'e' l'altro messaggio? "+ box.readMessage());
            System.out.println("Qual'e' il messaggio da parte di "+tre.getName()+"? "+ box.readMessage(tre));
        } catch (JAMMessageBoxException ex) {
            System.out.println(ex);
        }
        
    }
}
