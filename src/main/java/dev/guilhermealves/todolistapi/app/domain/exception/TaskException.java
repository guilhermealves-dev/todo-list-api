package dev.guilhermealves.todolistapi.app.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Guilherme
 */

@Getter
public class TaskException extends RuntimeException {

    private HttpStatus status;

    public TaskException(String error, HttpStatus status){
        super(error);
        this.status = status;
    }
}
