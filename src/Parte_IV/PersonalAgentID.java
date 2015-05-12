/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte_IV;



/**
 *
 * @author M746225
 */
public class PersonalAgentID extends CategoryAgentID {
    private String name;
        
        /**
         * Costruttore della Classe
         * @param name nome dell'agente
         * @param category categoria dell'agente
         */
        public PersonalAgentID(String name, String category){
            super(category);
            this.name = name;
        }          
        
        /**
         * Ritorna il nome dell'agente
         * @return name
         */
        public String getName(){
           return this.name;
        }
       
        /**
         * 
         * @return la categoria dell'agente
         */
        public String getCategory(){
            return super.getCategory();
        }
       
        /**
         * 
         * @param name il nuovo nome dell'agente
         */
        public void setName(String name){
           this.name = name;
        }
       
        /**
         * 
         * @param category la nuova categoria dell'agente
         */
        public void setCategory(String category){
           super.getCategory();
        }
       
        /**
         * 
         * @return la stringa di stampa del PersonalAgentID
         */
        public String toString(){
           return "(" + this.getName() + "," + this.getCategory() + ")" ;
        }
       
        /**
         * 
         * @param agente agente da confrontare con questo
         * @return true se sono uguali, se no false
         */
        public boolean equals(AgentID agente) {
           if (agente == null) throw new IllegalArgumentException("Il parametro AgenteId immesso e' nullo");
           return getName().equals(agente.getName()) && getCategory().equals(agente.getCategory());
        }
       
        /**
         * 
         * @param agentID agente da eguagliare
         * @throws IllegalArgumentException se L'oggetto inserito non è di tipo AgentID
         * @return se sono uguali o meno
         */
        public boolean equals(Object agentID) {
            try {
                return equals((AgentID) agentID);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Il parametro agentID non e' di tipo AgentID");
            }
        }  
} 