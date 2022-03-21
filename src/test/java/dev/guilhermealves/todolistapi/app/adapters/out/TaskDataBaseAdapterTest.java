package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.domain.repository.TaskRepository;
import dev.guilhermealves.todolistapi.app.utils.TaskTestUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

/**
 *
 * @author Guilherme
 */

@ExtendWith(MockitoExtension.class)
public class TaskDataBaseAdapterTest {
    
    @InjectMocks
    private TaskDataBaseAdapter taskDataBaseAdapter;

    @Mock 
    private TaskRepository repository;

    @Test
    public void mustCreate(){
        when(repository.saveAndFlush(any(Task.class))).thenReturn(TaskTestUtils.createTask(Role.USER));
        Task task = taskDataBaseAdapter.save(TaskTestUtils.createTask(Role.USER));
        assertNotNull(task);
    }
    
    @Test
    public void mustFindById(){
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(TaskTestUtils.createTask(Role.USER)));
        Optional<Task> opTask = taskDataBaseAdapter.findById(UUID.randomUUID());
        assertTrue(opTask.isPresent());
    }
    
    @Test
    public void mustFindAll(){
        when(repository.findAll()).thenReturn(TaskTestUtils.createListTask());
        List<Task> tasks = taskDataBaseAdapter.findAll();
        assertFalse(tasks.isEmpty());
    }
    
    @Test
    public void mustFindAllExample(){
        when(repository.findAll(any(Example.class))).thenReturn(TaskTestUtils.createListTask());
        List<Task> tasks = taskDataBaseAdapter.findAll(Example.of(TaskTestUtils.createTask(Role.USER)));
        assertFalse(tasks.isEmpty());
    }
    
    @Test
    public void mustFindOne(){
        when(repository.findOne(any(Example.class))).thenReturn(Optional.of(TaskTestUtils.createTask(Role.USER)));
        Optional<Task> opTask = taskDataBaseAdapter.findOne(Example.of(TaskTestUtils.createTask(Role.USER)));
        assertTrue(opTask.isPresent());
    }
    
    @Test
    public void mustDelete(){
        taskDataBaseAdapter.deleteById(UUID.randomUUID());
    }
}
