package dev.guilhermealves.todolistapi.app.adapters.in;

import dev.guilhermealves.todolistapi.app.domain.core.TaskCore;
import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import dev.guilhermealves.todolistapi.app.utils.TaskTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 *
 * @author Guilherme
 */

@ExtendWith(MockitoExtension.class)
public class TaskControllerAdapterTest {

    @InjectMocks
    private TaskControllerAdapter controllerAdapter;

    @Mock
    private TaskCore core;

    @Test
    public void mustCreateTask(){
        when(core.create(any(TaskModel.class))).thenReturn(TaskTestUtils.createTaskModel());
        ResponseEntity<TaskModel> entity = controllerAdapter.create(TaskTestUtils.createTaskModel());
        assertNotNull(entity);
        assertTrue(entity.getBody() instanceof TaskModel);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void mustFindById(){
        when(core.find(anyString())).thenReturn(TaskTestUtils.createTaskModel());
        ResponseEntity<TaskModel> entity = controllerAdapter.find(UUID.randomUUID().toString());
        assertNotNull(entity);
        assertTrue(entity.getBody() instanceof TaskModel);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void mustFindByStatus(){
        when(core.list(anyString())).thenReturn(TaskTestUtils.createListTaskModel());
        ResponseEntity<List<TaskModel>> entity = controllerAdapter.list(UUID.randomUUID().toString());
        assertNotNull(entity);
        assertFalse(entity.getBody().isEmpty());
        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void mustUpdateById(){
        when(core.update(anyString(), any(TaskModel.class))).thenReturn(TaskTestUtils.createTaskModel());
        ResponseEntity<TaskModel> entity = controllerAdapter.update(UUID.randomUUID().toString(), TaskTestUtils.createTaskModel());
        assertNotNull(entity);
        assertTrue(entity.getBody() instanceof TaskModel);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void mustDeleteById(){
        doNothing().when(core).delete(anyString());
        ResponseEntity<?> entity = controllerAdapter.delete(UUID.randomUUID().toString());
        assertNotNull(entity);
        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }
}