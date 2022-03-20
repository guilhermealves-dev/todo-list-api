package dev.guilhermealves.todolistapi.app.domain.core;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.mapper.TaskMapper;
import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import dev.guilhermealves.todolistapi.app.utils.TaskTestUtils;
import dev.guilhermealves.todolistapi.app.utils.UserTestUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Example;

/**
 *
 * @author Guilherme
 */

@ExtendWith(MockitoExtension.class)
public class TaskCoreTest {

    @InjectMocks
    private TaskCore core;

    @Mock
    private SecurityCore securityCore;

    @Mock
    private DataBaseIntegration taskDataBaseIntegration;

    private TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @BeforeEach
    public void before(){
        ReflectionTestUtils.setField(core, "taskMapper", taskMapper);
    }
    
    @Test
    public void mustCreateTask(){
        TaskModel taskModel = TaskTestUtils.createTaskModel();     
        User user = UserTestUtils.createUser(Role.USER);
        user.setIdUser(taskModel.getUser().getIdUser());
        
        Task task = taskMapper.mapper(taskModel, user);
        
        when(taskDataBaseIntegration.save(any(Task.class))).thenReturn(task);
        when(securityCore.getCurrentUser()).thenReturn(user);
        
        TaskModel newTask = core.create(taskModel);
        assertNotNull(newTask);
    }
    
    @Test
    public void mustGenerateExceptionForCreateTask(){
        TaskModel taskModel = TaskTestUtils.createTaskModel(); 
        User user = UserTestUtils.createUser(Role.USER);
                
        when(taskDataBaseIntegration.save(any(Task.class))).thenThrow(new CustomException(new Throwable("error")));
        when(securityCore.getCurrentUser()).thenReturn(user);
                
        CustomException ex = assertThrows(CustomException.class,
                ()->core.create(taskModel),
                "error");
        assertTrue(ex.getMessage().contains("error"));
    }

    @Test
    public void mustGenerateExceptionForTaskNotFound() {
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.empty());
        CustomException ex = assertThrows(CustomException.class,
                ()->core.find(UUID.randomUUID().toString()),
                "Task not found");
        assertTrue(ex.getMessage().contains("Task not found"));
    }

    @Test
    public void mustGenerateExceptionForFindNotAuthorized(){
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.of(TaskTestUtils.createTask(Role.ADMIN)));
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.USER));
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.find(UUID.randomUUID().toString()),
                "Not authorized");
        assertTrue(ex.getMessage().contains("Not authorized"));
    }
    
    @Test
    public void mustAdminFindTask(){
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.of(TaskTestUtils.createTask(Role.USER)));
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.ADMIN));
        TaskModel model = core.find(UUID.randomUUID().toString());
        assertNotNull(model);
    }
    
    @Test 
    public void mustUserFindYourTask(){
        Task task = TaskTestUtils.createTask(Role.USER);        
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.of(task));
        when(securityCore.getCurrentUser()).thenReturn(task.getUser());
        TaskModel model = core.find(UUID.randomUUID().toString());
        assertNotNull(model);
    }
    
    @Test
    public void mustAdminListAll(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.ADMIN));
        when(taskDataBaseIntegration.findAll()).thenReturn(TaskTestUtils.createListTask());
        List<TaskModel> tasks = core.list(null);
        assertFalse(tasks.isEmpty());
    }
    
    @Test
    public void mustUserListAll(){
        List<Task> tasks = TaskTestUtils.createListTask(); 
        User user = UserTestUtils.createUser(Role.USER);
        user.setIdUser(tasks.get(0).getUser().getIdUser());
        
        when(securityCore.getCurrentUser()).thenReturn(user);
        when(taskDataBaseIntegration.findAll(any(Example.class))).thenReturn(tasks);
        
        List<TaskModel> taskList = core.list(null);
        assertFalse(taskList.isEmpty());
    }
    
    @Test
    public void mustGenerateTaskExceptionNoTasksRegistered(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.USER));
        when(taskDataBaseIntegration.findAll(any(Example.class))).thenReturn(null);       
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.list(null),
                "No tasks registered");
        assertTrue(ex.getMessage().contains("No tasks registered"));
    }
    
    @Test
    public void mustGenerateTaskExceptionStatusNotFound(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.USER));
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.list("test"),
                "Status not found");
        assertTrue(ex.getMessage().contains("Status not found"));
    }
    
    @Test
    public void mustAdminListTasksWithStatusPending(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.ADMIN));
        when(taskDataBaseIntegration.findAll(any(Example.class))).thenReturn(TaskTestUtils.createListTask());
        
        List<TaskModel> taskList = core.list("pending");
        assertFalse(taskList.isEmpty());
        assertTrue(taskList.get(0).getStatus().equals(Status.PENDING));
    }
    
    @Test
    public void mustAdminGenerateTaskExceptionNoTaskRegisteredWithStatusCompleted(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.ADMIN));
        when(taskDataBaseIntegration.findAll(any(Example.class))).thenReturn(null);       
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.list("completed"),
                "No tasks registered with status completed");
        assertTrue(ex.getMessage().contains("No tasks registered with status completed"));
    }
    
    @Test
    public void mustUserListTasksWithStatusPending(){
        List<Task> tasks = TaskTestUtils.createListTask(); 
        User user = UserTestUtils.createUser(Role.USER);
        user.setIdUser(tasks.get(0).getUser().getIdUser());
        
        when(securityCore.getCurrentUser()).thenReturn(user);
        when(taskDataBaseIntegration.findAll(any(Example.class))).thenReturn(tasks);
        
        List<TaskModel> taskList = core.list("pending");
        assertFalse(taskList.isEmpty());
        assertTrue(taskList.get(0).getStatus().equals(Status.PENDING));
    }
    
    @Test
    public void mustUserGenerateTaskExceptionNoTaskRegisteredWithStatusCompleted(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.USER));
        when(taskDataBaseIntegration.findAll(any(Example.class))).thenReturn(null);       
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.list("completed"),
                "No tasks registered with status completed");
        assertTrue(ex.getMessage().contains("No tasks registered with status completed"));
    }
    
    @Test
    public void mustGenerateTaskExceptionTaskNotFoundOnUpdate(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.USER));
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.empty());
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.update(UUID.randomUUID().toString(), TaskTestUtils.createTaskModel()),
                "Task not found");
        assertTrue(ex.getMessage().contains("Task not found"));
    }
    
    @Test
    public void mustGenerateTaskExceptionNotAuthorizedOnUpdate(){
        User user = UserTestUtils.createUser(Role.USER);
        Task task = TaskTestUtils.createTask(Role.ADMIN);
        
        when(securityCore.getCurrentUser()).thenReturn(user);
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.of(task));
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.update(UUID.randomUUID().toString(), TaskTestUtils.createTaskModel()),
                "Not authorized");
        assertTrue(ex.getMessage().contains("Not authorized"));
    }
    
    @Test
    public void mustUpdateTask(){
        TaskModel taskModel = TaskTestUtils.createTaskModel(); 
        User user = UserTestUtils.createUser(Role.ADMIN);
        taskModel.getUser().setIdUser(user.getIdUser());
        
        Task task = taskMapper.mapper(taskModel, user);
        
        when(securityCore.getCurrentUser()).thenReturn(user);
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.of(task));
        
        taskModel.setStatus(Status.COMPLETED);
        
        TaskModel modelUpdated = core.update(taskModel.getIdTask().toString(), taskModel);
        
        assertNotNull(modelUpdated);
        assertTrue(modelUpdated.getStatus().equals(Status.COMPLETED));
        assertNotNull(modelUpdated.getInclusionDate());
    }
    
    @Test
    public void mustGenerateTaskExceptionTaskNotFoundOnDelete(){
        when(securityCore.getCurrentUser()).thenReturn(UserTestUtils.createUser(Role.ADMIN));
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.empty());
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.delete(UUID.randomUUID().toString()),
                "Task not found");
        assertTrue(ex.getMessage().contains("Task not found"));
    }
    
    @Test
    public void mustGenerateTaskExceptionNotAuthorizedOnDelete(){
        User user = UserTestUtils.createUser(Role.USER);
        when(securityCore.getCurrentUser()).thenReturn(user);
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.of(TaskTestUtils.createTask(Role.ADMIN)));
        
        CustomException ex = assertThrows(CustomException.class,
                ()->core.delete(UUID.randomUUID().toString()),
                "Not authorized");
        assertTrue(ex.getMessage().contains("Not authorized"));
    }
    
    @Test
    public void mustDelete(){
        User user = UserTestUtils.createUser(Role.USER);
        Task task = TaskTestUtils.createTask(Role.USER);
        task.setUser(user);
        
        when(securityCore.getCurrentUser()).thenReturn(user);
        when(taskDataBaseIntegration.findById(any(UUID.class))).thenReturn(Optional.of(task));
        assertDoesNotThrow(()-> core.delete(task.getIdTask().toString()));
    }
}