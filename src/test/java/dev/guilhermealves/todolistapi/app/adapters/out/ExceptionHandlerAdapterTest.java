/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import dev.guilhermealves.todolistapi.app.domain.model.error.FieldError;
import dev.guilhermealves.todolistapi.app.domain.model.error.TaskError;
import dev.guilhermealves.todolistapi.app.utils.ExceptionsUtils;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Guilherme
 */

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerAdapterTest {
    
    @InjectMocks
    private ExceptionHandlerAdapter exceptionHandlerAdapter;
    
    @Test
    public void mustCreateCustomExceptionDefault(){
        ResponseEntity<TaskError> entity = exceptionHandlerAdapter.customException(ExceptionsUtils.createCustomException());
        assertNotNull(entity);
        assertTrue(entity.getBody() instanceof TaskError);
        System.out.println(entity.getStatusCode());
        assertTrue(entity.getStatusCode().is5xxServerError());
    }
    
    @Test
    public void mustCreateTaskException(){
        ResponseEntity<TaskError> entity = exceptionHandlerAdapter.customException(ExceptionsUtils.createTaskException());
        assertNotNull(entity);
        assertTrue(entity.getBody() instanceof TaskError);
        System.out.println(entity.getStatusCode());
        assertTrue(entity.getStatusCode().is4xxClientError());
    }       
}
