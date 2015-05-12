/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM.TestFinaleII;
import JAM.*;

import java.util.*;

/**
 *
 * @author Gianmarco
 */
public class AstaBanditoreBehaviour extends JAMWhileBehaviour{
    private int baseAsta;
    private int offertaMax;
    private PersonalAgentID migliorOfferente;
    private CategoryAgentID partecipanti;
    private List<PersonalAgentID> offerentiList;
    private PersonalAgentID terminatore;
    private boolean asta;
    private int contattati;
    private int richiesta;
    private AstaBanditoreAgent a;
    
     public AstaBanditoreBehaviour(JAMAgent myAgent) {
        super(myAgent);
    }
    
    /*
     * inizializzo al valore di base l'oggetto in asta
     */ 
    public void setup() throws JAMBehaviourInterruptedException{
        baseAsta = 0;
        offertaMax = baseAsta;
        partecipanti = new CategoryAgentID("Cliente");
        a = (AstaBanditoreAgent) myAgent;
        a.setAsta(true);
        terminatore = new PersonalAgentID("Terminatore","Banditore");
        offerentiList = new LinkedList<PersonalAgentID>();
        contattati =0;
        richiesta=0;
        System.out.println("**Asta Iniziata**");
        Message msg = new Message(myAgent.getMyID(),partecipanti,Performative.INFORM,"Asta Iniziata");
        try {
            myAgent.send(msg);
        } catch (JAMADSLException ex) {
            System.out.println("Errore Durante L'invio del messaggio ai partecipanti");
                    }
        //System.out.println("Ho inviato:\n"+msg);
        }
    
    public void dispose() throws JAMBehaviourInterruptedException{
        //stampo statistiche su come è andata l'asta. chi si è aggiudicato l'oggetto e a quanto
        System.out.println("**Asta Terminata**");
        if(offertaMax == baseAsta){
            System.out.println("Nessuno ha partecipato all'asta!");            
        }
        else{
            System.out.println("Si e' aggiudicato l'asta "+migliorOfferente+" con un offerta di "+offertaMax);
            System.out.println("Comunico ad ogni partecipante il risultato...");
            inviaRisultati();
            
        }
        done();
    }
    
    /*
     * leggo un messaggio e lo analizzo
     * rispondo a seconda del messaggio letto
    */
    public void action() throws JAMBehaviourInterruptedException{
        try{
            Message msg = myAgent.receive();
            aggiungi((PersonalAgentID) msg.getSender());
            System.out.println("\n1 - Mi e' arrivato un messaggio da "+msg.getSender());
            if(msg.getPerformative().equals(Performative.QUERY_IF) && msg.getContent().equals("Valore corrente?")){
                System.out.println("    "+msg.getSender()+" vuole sapere qual'e' l'offerta corrente (asta="+a.getAsta()+")");
                if(a.getAsta()){
                    /* Incrementando richiesta solo se e' sempre lo stesso cliente a richiede informazioni
                     * e di conseguenza se e' l'unico in gara, interrompo prima l'asta
                     */
                    if(migliorOfferente != null && migliorOfferente.equals(msg.getSender()))richiesta++;
                    else richiesta =0;
                    System.out.println("Richiesta ="+richiesta+"\ncontattati="+contattati);
                    if(richiesta>1)a.setAsta(false);
                            
                    Message risposta = new Message(myAgent.getMyID(),msg.getSender(),Performative.INFORM,offertaMax+"\n"+migliorOfferente);
                    myAgent.send(risposta);
                    System.out.println("    Gli rispondo che la migliore offerta e' di "+migliorOfferente+", "+offertaMax );
                    
                }
                else { 
                    Message risposta = new Message(myAgent.getMyID(),msg.getSender(),Performative.REFUSE,offertaMax+"\n"+migliorOfferente);
                    myAgent.send(risposta);
                    System.out.println("    Gli rispondo che la migliore offerta e' di "+migliorOfferente+", "+offertaMax+", ma l'asta e' terminata");
                    contattati++;    
                }                
            }
            else if(msg.getPerformative().equals(Performative.REQUEST)){
                System.out.println("    Vuole fare una nuova offerta!");
                int offerta = Integer.parseInt(msg.getContent());
                System.out.println("    Mi ha offerto "+offerta+" contro i "+offertaMax+" del migliore offerente");
                if(offerta<=offertaMax){
                    msg = new Message(myAgent.getMyID(),msg.getSender(),Performative.REFUSE,"Offerta troppo bassa");
                    System.out.println("    Offerta Troppo bassa");
                    myAgent.send(msg);
                }
                else{
                    offertaMax = offerta;
                    migliorOfferente = (PersonalAgentID)msg.getSender();
                    msg = new Message(myAgent.getMyID(),msg.getSender(),Performative.INFORM,"Offerta accettata!");
                     System.out.println("    Gli comunico che ho accettato l'offerta");
                    myAgent.send(msg);
                }                              
            }
            else if(msg.getPerformative().equals(Performative.INFORM) && msg.getContent().equals("Mi Arrendo")){
                System.out.println("****"+msg.getSender()+" si arrende ****");
                contattati++;
            }
            if(contattati == offerentiList.size())done();       
        } catch(NumberFormatException e){
                System.out.println("Non vi e' un offerta valida nel content");
        } 
        catch(JAMADSLException ex){
            System.out.println(ex);
        }        
    }
    
    
    /* 
     * Questo metodo invia il responso a tutti quelli che hanno partecipato all'asta
     */    
    public void inviaRisultati(){
        try {
            Message msg = null;
            for(PersonalAgentID agente : offerentiList){
                System.out.println("...invio a "+agente);
                if(agente.equals(migliorOfferente)) msg= new Message(myAgent.getMyID(),agente,Performative.INFORM,"you win!");
                else msg = new Message(myAgent.getMyID(),agente,Performative.INFORM,"you lose!");
                 myAgent.send(msg);                
            }
        } catch (JAMADSLException ex) {
            System.out.println("Errore "+ex);
        }
    }
    
    /*
     * Aggiunge i clienti alla lista dei partecipanti all'asta 
     */
    public void aggiungi(PersonalAgentID a){
        if(!a.getCategory().equals("Cliente")) return;
        for(PersonalAgentID agente : offerentiList)
            if(agente.equals(a)) return;        
        offerentiList.add(a);
    }    
    
    
}
