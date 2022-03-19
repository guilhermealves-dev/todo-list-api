/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.core;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.exception.TaskException;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guilherme
 */

@Slf4j
@Service
public class TaskCore {
    
    @Autowired
    @Qualifier("task")
    private DataBaseIntegration taskDataBaseIntegration;
    
    @Autowired
    @Qualifier("user")
    private DataBaseIntegration userDataBaseIntegration;
    
    public Task create(Task task){
        try{
            Optional<User> user = userDataBaseIntegration.findById(task.getUser().getIdUser());

            if(!user.isPresent()){
                throw new TaskException("User not found", HttpStatus.BAD_REQUEST);
            }
            
            task.setInclusionDate(LocalDateTime.now());
            task.setUser(user.get());
            Task newTask = (Task) taskDataBaseIntegration.save(task);
            return clearSensitiveDataFromTask(newTask);
            
        }catch(Throwable t){
            log.error("Error create task: ", t);
            throw new CustomException(t);
        }
    }
    
    public Task find(String id){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Task> task = taskDataBaseIntegration.findById(uuid);
            
            if(!task.isPresent()){
                throw new TaskException("Task not found", HttpStatus.BAD_REQUEST);
            }
            
            return clearSensitiveDataFromTask(task.get());
            
        }catch(Throwable t){
            log.error("Error find task: ", t);
            throw new CustomException(t);
        }
    }
    
    public List<Task> list(String status){
        try{
            if(Objects.isNull(status)){
                List<Task> tasks = taskDataBaseIntegration.findAll();

                if(tasks.isEmpty()){
                    throw new TaskException("No tasks registered", HttpStatus.BAD_REQUEST);
                }
                
                Collections.sort(tasks);                        
                return clearSensitiveDataFromTasks(tasks);
            }
            
            Status statusFilter = Status.fromString(status);
            if(Objects.isNull(statusFilter)){
                throw new TaskException("Status not found", HttpStatus.BAD_REQUEST);
            }
            
            Task taskFilter = new Task();
            taskFilter.setStatus(statusFilter);

            Example<Task> ex = Example.of(taskFilter);
            List<Task> tasks = taskDataBaseIntegration.findAll(ex);

            if(tasks.isEmpty()){
                throw new TaskException("No tasks registered with status "+ status.toString(), HttpStatus.BAD_REQUEST);
            }

            return clearSensitiveDataFromTasks(tasks);

        }catch(Throwable t){
            log.error("Error list tasks by status: {}", t);
            throw new CustomException(t);
        }
    }
    
    public List<Task> listByUser(String userId){
        try{
            UUID uuid = UUID.fromString(userId);
            Optional<User> user = userDataBaseIntegration.findById(uuid);
            if(!user.isPresent()){
                throw new TaskException("User not found", HttpStatus.BAD_REQUEST);
            }
            
            Task taskFilter = new Task();
            taskFilter.setUser(user.get());

            Example<Task> ex = Example.of(taskFilter);
            List<Task> tasks = taskDataBaseIntegration.findAll(ex);

            if(tasks.isEmpty()){
                throw new TaskException("No tasks registered by user "+ user.get().getUsername(), HttpStatus.BAD_REQUEST);
            }

            Collections.sort(tasks);
            return clearSensitiveDataFromTasks(tasks);

        }catch(Throwable t){
            log.error("Error list tasks by user: {}", t);
            throw new CustomException(t);
        }
    }
    
    public Task update(String id, Task task){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Task> opTask = taskDataBaseIntegration.findById(uuid);
            
            if(!opTask.isPresent()){
                throw new TaskException("Task not found", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            
            Task taskUpdate = opTask.get();
            if(!Objects.isNull(task.getTitle())){
                taskUpdate.setTitle(task.getTitle());
            }
            
            if(!Objects.isNull(task.getDescription())){
                taskUpdate.setDescription(task.getDescription());
            }
            
            if(!Objects.isNull(task.getStatus()) && !taskUpdate.getStatus().equals(task.getStatus())){
                taskUpdate.setStatus(task.getStatus());
                taskUpdate.setModificationDate(LocalDateTime.now());
            }
            
            taskDataBaseIntegration.save(taskUpdate);
            return clearSensitiveDataFromTask(taskUpdate);
        }
        catch(Throwable t){
            log.error("Error update task: {}", t);
            throw new CustomException(t);
        }
    }
    
    public void delete(final String id){
        try{
            UUID uuid = UUID.fromString(id);
            taskDataBaseIntegration.deleteById(uuid);
        }
        catch(Throwable t){
            throw new CustomException(t);
        }
    }
    
    private Task clearSensitiveDataFromTask(Task task){
        task.getUser().setPassword(null);
        
        return task;
    }
    
    private List<Task> clearSensitiveDataFromTasks(List<Task> tasks){
        for(Task task : tasks){
            clearSensitiveDataFromTask(task);
        }
        
        return tasks;
    }
}
