/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.exception.TaskException;
import dev.guilhermealves.todolistapi.app.domain.model.FieldError;
import dev.guilhermealves.todolistapi.app.domain.model.TaskError;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Guilherme
 */

@RestControllerAdvice
public class ExceptionHandlerAdapter extends ResponseEntityExceptionHandler {
    
    private final static String DEFAULT_MSG = "Unexpected error";
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<TaskError> customException(CustomException t) {
        TaskError taskError = new TaskError();
        
        if(ExceptionUtils.getRootCause(t) instanceof TaskException){
            TaskException te = (TaskException) ExceptionUtils.getRootCause(t);
            
            taskError.setMessage(te.getError());
            taskError.setStatus(te.getStatus().value());
            
            return new ResponseEntity<>(taskError, te.getStatus()); 
        }
        
        taskError.setMessage(DEFAULT_MSG);
        taskError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return new ResponseEntity<>(taskError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<FieldError>> camposException(ConstraintViolationException ex) {
        List<FieldError> errors = new ArrayList<>();
        
        ex.getConstraintViolations().parallelStream().forEach(constraintViolation->{
            FieldError field = new FieldError();
            field.setMessage(constraintViolation.getMessage());
            field.setValue(String.valueOf(constraintViolation.getInvalidValue()));
            
            AtomicReference<String> name = new AtomicReference();
            constraintViolation.getPropertyPath().forEach(path->{
                name.set(path.getName());
            });
                    
            field.setField(name.get());
            errors.add(field);
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().parallelStream().forEach(error->{
            FieldError field = new FieldError();
            field.setField(error.getField());
            field.setValue(String.valueOf(error.getRejectedValue()));
            field.setMessage(error.getDefaultMessage());
            
            errors.add(field);
        });

        return new ResponseEntity<>(errors, status);
    }
    
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> errors = new ArrayList<>();ex.getMessage();

        FieldError field = new FieldError();
        field.setField(ex.getParameterName());
        field.setMessage(ex.getMessage());
        
        errors.add(field);

        return new ResponseEntity<>(errors, status);
    }
}
