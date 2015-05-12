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
public class JAMADSLException extends Exception {

    public JAMADSLException(Throwable cause) {
        super(cause);
    }

    public JAMADSLException(String message, Throwable cause) {
        super(message,cause);
    }

    public JAMADSLException(String message) {
        super(message);
    }

    public JAMADSLException() {
        super();
    }
}
