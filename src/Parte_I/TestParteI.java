

package Parte_I;


import java.util.*;

public class TestParteI {
    public static void main(String[] args) {
        AgentID agenteCercato;
        List<AgentID> listaTrovati;

        List<AgentID> lista = new LinkedList<AgentID>();
        lista.add(new PersonalAgentID("Matteo", "Baldoni"));
        lista.add(new PersonalAgentID("Mario", "Rossi"));
        lista.add(new PersonalAgentID("Andrea", "Rossi"));
        lista.add(new PersonalAgentID("Alfredo", "Baldoni"));
        lista.add(new PersonalAgentID("Alfredo", "Rossi"));
        lista.add(new PersonalAgentID("Mercoledi", "Adams"));  
        
        agenteCercato = new PersonalAgentID("Mario", "Rossi");
        listaTrovati = new LinkedList<AgentID>();
        for (AgentID agent : lista)
            if (agenteCercato.equals(agent))
                listaTrovati.add(agent);
        for (AgentID agent : listaTrovati)
            System.out.println(agent);
        System.out.println("---");
        agenteCercato = new CategoryAgentID("Rossi");
        listaTrovati = new LinkedList<AgentID>();
        for (AgentID agent : lista)
            if (agenteCercato.equals(agent))
                listaTrovati.add(agent);
        for (AgentID agent : listaTrovati)
            System.out.println(agent);
        System.out.println("---");
        agenteCercato = new GenericAgentID();
        listaTrovati = new LinkedList<AgentID>();
        for (AgentID agent : lista)
            if (agenteCercato.equals(agent))
                listaTrovati.add(agent);
        for (AgentID agent : listaTrovati)
            System.out.println(agent);
        
    }
}
