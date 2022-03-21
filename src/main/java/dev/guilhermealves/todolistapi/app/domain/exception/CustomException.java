package dev.guilhermealves.todolistapi.app.domain.exception;

/**
 *
 * @author Guilherme
 */

public class CustomException extends RuntimeException {
    
    public CustomException(final Throwable t){
        super(t);
    }
}
