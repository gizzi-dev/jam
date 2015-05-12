
package JAM;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author M746225
 */
public class ADSLImpl extends UnicastRemoteObject implements ADSL{
    private List<RemoteMessageBox> messagesBoxes;
    private int port;
    private String name;
    private String ip;
    private java.util.Vector listeners = new java.util.Vector();
    
    
    
    
    public ADSLImpl() throws RemoteException{
        this("adsl", 1099);
    }
    
    public ADSLImpl(String name,int port) throws RemoteException{
    this.port =port;
    this.name = name;
    this.ip = "127.0.0.1";
    messagesBoxes = new LinkedList<RemoteMessageBox>();
    }
    
    /**
     * restituisce una lista di riferimenti ad oggetti (remoti)
     * di tipo RemoteMessageBox i cui proprietarisono uguali a agentID.
     * @param agente l'agente possessore della RemoteMessageBox
     * @return List RemoteMessageBox
     */
    public List<RemoteMessageBox> getRemoteMessageBox(AgentID agente){ 
        List<RemoteMessageBox> box = new LinkedList<>();
        if (agente == null) {
            throw new IllegalArgumentException("Il parametro inserito è nullo");
        }         
        synchronized(this){
            for (RemoteMessageBox msg : messagesBoxes) {
                try {
                    if(((agente.getName().equals(msg.getOwner().getName() )) && (agente.getName() != "") && (agente.getCategory() != "") && (agente.getCategory().equals(msg.getOwner().getCategory())))  ||
                            (agente.getName() == "" && agente.getCategory().equals(msg.getOwner().getCategory())) ||
                            (agente.getName() == "" && agente.getCategory() == "")) {
                        box.add(msg);          
                        notifyConsoleChange("Ritorno box "+msg.getOwner());
                        System.out.println("Ritorno box "+msg.getOwner());
                    }               
                } catch (RemoteException ex) {
                    System.out.println("Errore RemoteException nella getRemoteMessageBox");
                }
            }
        }        
        return box;
    }
    
    /**
     * Richiede l'inserimento di un messageBox presso l'ADSL, se l'elemento è già presente non viene
     * effettuata alcuna operazione e viene lanciata un'opportuna eccezione
     * @param messageBox messageBox da inserire
     * @throws JAMADSLException viene lanciata nel caso la RemoteMessageBox sia già presente
     * 
     */
    public void insertRemoteMessageBox(RemoteMessageBox messageBox) throws JAMADSLException{
        if (messageBox == null) {
            throw new IllegalArgumentException("Il parametro inserito è nullo");
        }
         
        synchronized(this){
            for(RemoteMessageBox msg : messagesBoxes){
                try{
                    if(msg.getOwner().equals(messageBox.getOwner())){
                        notifyConsoleChange("MessageBox di "+messageBox.getOwner()+" gia' presente!");
                        System.out.println("MessageBox di "+messageBox.getOwner()+" gia' presente!");                        
                        throw new JAMADSLException("RemoteMessageBox gia' presente");
                    }   
                }catch (RemoteException e) {
                    System.out.println("Errore all'inserimento della Remote MessageBox");
                }               
            }            
        this.messagesBoxes.add(messageBox);
        try {
            notifyConsoleChange("MessageBox di "+messageBox.getOwner()+" aggiunta!");
            } catch (RemoteException ex) {
               System.out.println(ex);
            }        
        } 
    } 
    
    /**
     * e richiede la cancellazione dell’oggetto RemoteMessageBox presente presso l’ADSL di propriet`a dell’agente agentID.
     * Se ’elemento non `e presente non viene effettuata alcuna operazione e viene lanciata
     * un’opportuna eccezione.
     * @param agente l'agente possessore della RemoteMessageBox da eliminare     * 
     * @throws JAMADSLException nel caso la RemoteMessageBox non sia presente
     */
    public void removeRemoteMessageBox(AgentID agente) throws JAMADSLException {
        if (agente == null) {
            throw new IllegalArgumentException("Il parametro inserito è nullo");
        }       
       boolean cancellato = false;
    
        synchronized(this){
            for(RemoteMessageBox msg : messagesBoxes ){
                try {
                    if (agente.equals(msg.getOwner())) {          
                        messagesBoxes.remove(msg);
                        cancellato = true;
                        notifyConsoleChange("Cancellato box "+agente.toString());
                        System.out.println("Cancellato box "+agente.toString());                        
                    }
                } catch (RemoteException ex) {                    
                    System.out.println("Errore alla rimozione della Remote MessageBox");
                    notifyConsoleChange("Errore alla rimozione della Remote MessageBox");
                }
            }
        }
        if (!cancellato) { 
            notifyConsoleChange("removeRemoteMessagebox non riuscita perchè l'elemento non e' presente!");
            throw new JAMADSLException("RemoteMessageBox non presente");
        }
    }
    
    /**
     * Registra l'adsl
     * @throws RemoteException in caso di errore
     */
    public void startRMIRegistry() throws RemoteException{
        java.rmi.registry.LocateRegistry.createRegistry(this.port);
        notifyConsoleChange("startRMI Eseguito!");
    }
    
    /**
     * Esegue il rebind
     * @throws RemoteException in caso di errore
     * @throws MalformedURLException in caso l'url sia malformato
     */
    public void startADSL()throws RemoteException, MalformedURLException {
        Naming.rebind("//" + this.ip + ":" + this.port + "/" + this.name, this);
        notifyConsoleChange("startADSL Eseguito!");
    }
    
    /**
     * Esegue L'unbind
     * @throws RemoteException in caso di errore
     * @throws NotBoundException in caso non sia stato effettuato il bind
     * @throws MalformedURLException  in caso di url malformato
     */
    public void stopADSL()throws RemoteException, NotBoundException, MalformedURLException {
        Naming.unbind("//" + this.ip + ":" + this.port + "/" + this.name);
        notifyConsoleChange("stopADSL Eseguito!");
    }
    
    /**
     * Metodo che server per notificare alla console 
     * @param count la stringa da inserire nella console
     */
    protected void notifyConsoleChange(String count) {
        java.util.Vector tmpList;
        ConsoleEvent ce = new ConsoleEvent(this, count);
        synchronized(this) {
            tmpList = (java.util.Vector) listeners.clone();
        }
        for (int i=0; i<tmpList.size(); i++) {
           ((ConsoleChangeListener)tmpList.elementAt(i)).ConsoleChange(ce);
        }
      
    }
    
    /**
     * Aggiunge un listener alla console
     * @param ccl il listener da aggiungere
     * @throws java.util.TooManyListenersException in caso la lista listeners sia piena
     */
    public void addConsoleChangeListener(ConsoleChangeListener ccl) throws java.util.TooManyListenersException {
        listeners.addElement(ccl);
    }
    
    /**
     * rimuove il listener dalla console
     * @param ccl il listener da rimuovere
     */
    public synchronized void removeConsoleChangeListener(ConsoleChangeListener ccl){
        listeners.removeElement(ccl);
    }
    
}
