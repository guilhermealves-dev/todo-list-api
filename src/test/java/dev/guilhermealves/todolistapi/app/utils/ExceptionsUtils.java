/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.utils;

import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.exception.TaskException;
import dev.guilhermealves.todolistapi.app.domain.model.error.TaskError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Guilherme
 */

public class ExceptionsUtils {
    
    public static ResponseEntity<TaskError> createDefaultException(){
        TaskError taskError = new TaskError();
        taskError.setMessage("Unexpected error");
        taskError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return new ResponseEntity<>(taskError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public static CustomException createCustomException(){
        CustomException customException = new CustomException(new Throwable("Error"));
        return customException;
    }
    
    public static CustomException createTaskException(){
        TaskException taskException = new TaskException("Error message", HttpStatus.BAD_REQUEST);
        CustomException customException = new CustomException(taskException);
        return customException;
    }
    
}
