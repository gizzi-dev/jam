package Parte_IV;

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
     * @param agentID agente da confrontare
     * @return boolean
     */
    public boolean equals(Object agentID);

    /**
     * Restituisce il valore corrente del campo name di tipo String che
     * rappresenta il nome simboli dell'agente
     *
     * @return string
     */
    public String getName();

    /**
     * Restituisce il valore corrente del campo category di tipo String che
     * rappresenta la categoria a cui appartiene l'agente ("es. "time_provider",
     * "time_requester")
     *
     * @return string
     */
    public String getCategory();

    /**
     * restituisce un oggetto di tipo String che rappresenta l'oggetto stesso
     *
     * @return string
     */
    public String toString();

}
