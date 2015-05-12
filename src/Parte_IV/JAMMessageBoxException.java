/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte_IV;

/**
 *
 * @author Gianmarco
 */
public class JAMMessageBoxException extends Exception {

    public JAMMessageBoxException() {
        super();
    }

    public JAMMessageBoxException(String msg) {
        super(msg);
    }

    public JAMMessageBoxException(Throwable cause) {
        super(cause);
    }

    public JAMMessageBoxException(String message, Throwable cause) {
        super(message,cause);
    }
}
