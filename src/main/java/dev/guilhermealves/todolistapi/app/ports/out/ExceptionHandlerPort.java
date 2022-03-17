/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.ports.out;

import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.model.FieldError;
import dev.guilhermealves.todolistapi.app.domain.model.TaskError;
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
