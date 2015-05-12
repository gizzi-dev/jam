/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM.TestFinaleII;
import JAM.*;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author M746225
 */
public class AstaClienteBehaviour extends JAMWhileBehaviour{
    private int saldoInit;
    private boolean aggiudicato;
    private int offerta;
    private CategoryAgentID banditore;
    private boolean astaStart;
    private LogFile log;
   
    
    public AstaClienteBehaviour(JAMAgent myAgent) {
        super(myAgent);
        log = new LogFile();
    }
    
    /* inizializzo il mio portafoglio con una dotazione di euro 
     * a caso compreso in un certo range di valori
     */
    public void setup() throws JAMBehaviourInterruptedException{
        this.saldoInit = (int) (Math.random() * 60000);
        try {
            log.startLog(myAgent.getMyID().toString().trim(), "Log file per " + myAgent.getMyID());
            log.log("0 - Inizializzazione comportamento.");
            log.log("-->Saldo iniziale di "+getSaldoInit()+"\n");
        } catch(JAMIOException err) {
            System.out.println("Errore: "+ err);
            done();
        }   
        this.aggiudicato=false;
        this.offerta = 0;
        astaStart=false;
        
    }
    
    /* stampo il risultato dell'asta, se mi sono aggiudicato
     * o no l'oggetto e ne caso quanto ho speso
     */
    public void dispose() throws JAMBehaviourInterruptedException{
        try{
            log.log("18 - **Per me l'asta e' Terminata!**");
            log.log("19 - Aspetto il responso");
            Message msg =myAgent.receive(banditore);
            System.out.println(msg);
            if(msg.getContent().equals("you win!")) 
                log.log("20 - Ho vinto l'asta spendendo "+offerta+" di "+saldoInit);
            else log.log("21 - Non sono riuscito ad aggiudicarmi l'offerta, sarà per un altra volta!");
        }catch(JAMIOException err) {
            System.out.println("Errore: " + err);
        } finally {done();}
       
    }
    
    /* 1) se ho i soldi nel portafoglio chiedo il valore corrente
     *    dell'oggetto in asta
     * 2) se ancora in asta e non sono io il migliore offerente
     *    effettuo una offerta superiore a quella(es incrementando di 
     *    un valore random l'offerta corrente)
     * 3) invio la mia offerta e attendo la risposta
     * 4) se la mia offerta è accettata, mi risposo qualche secondo
     *    e poi vado nuovamente a verificare se sono rimasto io il
     *    miglior offerente
     * 5) se la mia offerta non è accettata ricomincio da capo
     * 6) se non ho soldi sufficienti per rilanciare mi ritiro 
     *    rispondo a seconda del messaggio letto    
     */
    public void action() throws JAMBehaviourInterruptedException{
        try {
            Message msg;
             System.out.println("Arrivo qua");
            if(!astaStart){
                log.log("1 - Aspetto l'inizio dell'asta!");
                msg = myAgent.receive();
                while(!msg.getContent().equals("Asta Iniziata")) msg = myAgent.receive();
                banditore = (CategoryAgentID) msg.getSender();
                log.log("2 - Asta iniziata!");
                astaStart = true;
            }
            if(this.saldoInit==0){
              log.log("3 - Non ho piu' soldi! Non posso aggiudicarmi l'oggetto");
              done();
            }
            log.log("\n4 - Chiedo al banditore il valore dell'offerta corrente");
            Message request = new Message(myAgent.getMyID(),banditore, Performative.QUERY_IF, "Valore corrente?");        
            myAgent.send(request);
            log.log("5 - Aspetto la risposta del banditore!");
            msg = myAgent.receive(banditore);
            log.log("6 - Risposta arrivata ");
            if(msg.getPerformative() == Performative.REFUSE){
                log.log("7 - L'oggetto e' già stato aggiudicato! - REFUSE");
                if (!msg.getContent().equals(myAgent.getMyID().getName())) {
                        aggiudicato = true;
                    }
                done();
            }
            else if(msg.getPerformative() == Performative.INFORM){                
                log.log("8 - L'oggetto non e' stato aggiudicato - INFORM");
                Scanner Messaggio = new Scanner((String) msg.getContent());
                int miglioreOfferta = Integer.parseInt(Messaggio.nextLine());
                String migliorOfferente = Messaggio.nextLine();
                if(myAgent.getMyID().toString().equals(migliorOfferente)){
                    log.log("9 - Il miglior offerente sono io!");
                    sleep(2000);
                }
                else if(saldoInit < miglioreOfferta){
                    log.log("10 - Non ho abbastanza soldi per rilanciare! (saldo: "+saldoInit+"; offerta: "+miglioreOfferta+")");
                    Message arresa = new Message(myAgent.getMyID(),banditore,Performative.INFORM,"Mi Arrendo");
                    myAgent.send(arresa);
                    done();                    
                }
                else{
                    log.log("11 - Ho abbastanza soldi per rilanciare!");                
                    this.offerta  = ((int) (Math.random() * (saldoInit-miglioreOfferta)))+miglioreOfferta;
                    log.log("12 - Rilancio con un offerta di "+offerta);
                    Message msgOfferta = new Message(myAgent.getMyID(),banditore,Performative.REQUEST,offerta+"");
                    myAgent.send(msgOfferta);
                    log.log("13 - Aspetto la risposta dal banditore");
                    Message rispOfferta = myAgent.receive(banditore);
                    if(rispOfferta.getPerformative() == Performative.REFUSE){
                        log.log("14 - Il banditore non ha accettato la mia offerta");
                    }
                    else if(rispOfferta.getPerformative() == Performative.INFORM){
                        log.log("15 - La mia offerta e' stata accettata!");
                    }
                    else log.log("16 - Risposta non capita "+rispOfferta.getPerformative());
                }
                
            }
            else  log.log("17 - Risposta non capita "+msg.getPerformative()+" dopo la 8");
            
            
        } catch (Exception ex) {
            System.out.println("("+myAgent.getMyID()+")Error "+ex);
        }   
        
        
        
            
    }
    
    public int getSaldoInit(){
         return this.saldoInit;         
    }
    
    
    
    
    
    
    
}
