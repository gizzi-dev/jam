
package Parte_IV;




/**
 *
 * @author M.746225
 */
public class CategoryAgentID extends GenericAgentID {
    private String category;
    
    /**
     * Costrutore della classe CategoryAgentID
     * @param category la categoria dell'agente
     */
    public CategoryAgentID(String category){ 
            this.category = category;             
        }
    
    /**
     * 
     * @return La categoria dell'agente
     */
    public String getCategory(){
           return this.category;
       }
    
    /**
     * Imposta la categoria dell'agente
     * @param category la nuova categoria da inserire
     */
    public void setCategory(String category){
           this.category = category;
       }
    
    /**
     * 
     * @return Stringa contentenente la stampa della categoria dell'agente
     */
    public String toString(){
           return "(," + this.getCategory() + ")" ;
       }
    
    /**
     * 
     * @param agentID L'agente da confrontare con questo     * 
     * @return Se le due categoriesono uguali, torna TRUE se no FALSE
     */
    public boolean equals(AgentID agentID) {
        if (agentID == null) throw new IllegalArgumentException("Errore - Il parametro Agente e' null");
        return getCategory().equals(agentID.getCategory());
    }
       
    /**
     * 
     * @param agentID L'agente da confrontare con questo      
     * @return Se le due categoriesono uguali, torna TRUE se no FALSE
     * @throws IllegalArgumentException se il parametro non è di tipo agente
     */
    public boolean equals(Object agentID) {
        try {
            return equals((AgentID) agentID);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Il parametro agentID non e' di tipo AgentID");
        }
    }
}
    
