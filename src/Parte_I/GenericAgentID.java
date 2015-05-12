
package Parte_I;


/**
 *
 * @author M 746225
 */
public class GenericAgentID implements AgentID  {          
      
    /**
     * Costruttore della classe GenericAgentID
     */
    public GenericAgentID(){               
    }      
   
    /**
     * 
     * @return La stringa "(,)" in quanto un GenericAgentID non possiede ne nome
     * ne categoria
     */
    public String toString(){
        return "(,)" ;
    }
    
    /**
     * 
     * @param agentID L'agente da confrontare
     * @return se i due sono agenti o meno
     */
    public boolean equals(AgentID agentID) {
        if (agentID == null)  throw new IllegalArgumentException("Errore - Il parametro Agente e' null");
        return true;
    }
    
    /**
     * 
     * @param agentID agente da eguagliare
     * @return true o false, se due agenti sono uguali o meno
     * @throws IllegalArgumentException se l'oggetto agendID non è di tipo AgendID
     */
    public boolean equals(Object agentID) {
        try {
            return equals((AgentID) agentID);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Il parametro agentID non e' di tipo AgentID");
        }
    }

    /**
     * 
     * @return la Stringa contenent il nome
     */
    public String getName() {
        return "";
    }

    /**
     * 
     * @return la Stringa contenente la categoria
     */
    public String getCategory() {
        return "";
    }
    
}
