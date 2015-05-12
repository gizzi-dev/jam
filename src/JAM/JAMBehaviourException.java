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
public class JAMBehaviourException extends JAMException {
    
    public  JAMBehaviourException(Throwable cause) {
        super(cause);
    }

    public  JAMBehaviourException(String message, Throwable cause) {
        super(message,cause);
    }

    public  JAMBehaviourException(String message) {
        super(message);
    }

    public  JAMBehaviourException() {
        super();
    }
    
}
