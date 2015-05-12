/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAM;

/**
 *
 * @author Gianmarco
 */
public class JAMException extends Exception {

    public JAMException(String message, Throwable cause) {
        super(message, cause);
    }

    public JAMException(Throwable cause) {
        super(cause);
    }

    public JAMException(String message) {
        super(message);
    }

    public JAMException() {
        super();
    }
}
