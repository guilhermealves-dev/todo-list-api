package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.model.error.TaskError;
import dev.guilhermealves.todolistapi.app.utils.ExceptionsUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
