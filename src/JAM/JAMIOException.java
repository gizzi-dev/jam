package JAM;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author M746225
 */
public class JAMIOException extends JAMException {
    
    public JAMIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public JAMIOException(Throwable cause) {
        super(cause);
    }

    public JAMIOException(String message) {
        super(message);
    }

    public JAMIOException() {
        super();
    }
}
