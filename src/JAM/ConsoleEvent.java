/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

/**
 *
 * @author M746225
 */
public class ConsoleEvent extends java.util.EventObject{
    private String text;
    
    /**
     * costruttore
     * @param source oggetto osservato
     * @param text testo da scrivere in console
     */    
    ConsoleEvent(Object source, String text) {
        super(source);
        this.text = text;
    }
    
    /**
     * Rtirona la stringa di testo da mettere in console
     * @return String
     */
    public String getText() {
        return text;
    }
}

