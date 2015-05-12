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
public class JAMBehaviourInterruptedException extends JAMBehaviourException {
    
    public  JAMBehaviourInterruptedException(Throwable cause) {
        super(cause);
    }

    public  JAMBehaviourInterruptedException(String message, Throwable cause) {
        super(message,cause);
    }

    public  JAMBehaviourInterruptedException(String message) {
        super(message);
    }

    public  JAMBehaviourInterruptedException() {
        super();
    }
}
