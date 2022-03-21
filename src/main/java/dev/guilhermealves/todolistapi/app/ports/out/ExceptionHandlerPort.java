package dev.guilhermealves.todolistapi.app.ports.out;

import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.model.error.FieldError;
import dev.guilhermealves.todolistapi.app.domain.model.error.TaskError;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Guilherme
 */

public interface ExceptionHandlerPort {
    
    public ResponseEntity<TaskError> customException(CustomException ex);
    public ResponseEntity<List<FieldError>> camposException(ConstraintViolationException ex); 
}
