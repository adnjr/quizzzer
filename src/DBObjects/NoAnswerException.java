/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBObjects;

/**
 *
 * @author Armando
 */
public class NoAnswerException extends RuntimeException {

    public NoAnswerException() {
    }

    public NoAnswerException(String message) {
        super(message);
    }

    public NoAnswerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAnswerException(Throwable cause) {
        super(cause);
    }
    
}
