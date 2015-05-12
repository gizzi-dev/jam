package JAM;

import java.io.Serializable;




/**
 *
 * @author M746225
 */
public interface AgentID extends Serializable {

    /**
     * Confronta un oggetto della classe che implementa l'interfaccia AgentID
     * con un oggetto di tipo AgentID. Restituisce true se l'identicatore
     * dell'agente passato come parametro ha lo stesso nome e appartiene alla
     * stessa categoria dell'oggetto su cui e invocato il metodo, false
     * altrimenti; (fare attenzione quando si confronta un oggetto di tipo
     * GenericAgentID e CategoryAgentID con un oggetto di tipo personalAgentID)
     *
     * @param agentID l'agente da comparare
     * @return boolean
     */
    public boolean equals(Object agentID);

    /**
     * Restituisce il valore corrente del campo name di tipo String che
     * rappresenta il nome simboli dell'agente
     *
     * @return stringa
     */
    public String getName();

    /**
     * Restituisce il valore corrente del campo category di tipo String che
     * rappresenta la categoria a cui appartiene l'agente ("es. "time_provider",
     * "time_requester")
     *
     * @return stringa
     */
    public String getCategory();

    /**
     * restituisce un oggetto di tipo String che rappresenta l'oggetto stesso
     *
     * @return stringa
     */
    public String toString();

}
