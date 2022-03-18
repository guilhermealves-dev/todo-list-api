/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.core;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.exception.TaskException;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
            
            Optional<User> user = userDataBaseIntegration.find(task.getUser().getIdUser());

            if(!user.isPresent()){
                throw new TaskException("User not found", HttpStatus.BAD_REQUEST);
            }
            
            task.setInclusionDate(LocalDateTime.now());
            task.setUser(user.get());
            Task newTask = (Task) taskDataBaseIntegration.save(task);
            return newTask;
            
        }catch(Throwable t){
            log.error("Error create task: ", t);
            throw new CustomException(t);
        }
    }
    
    public Task find(String id){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Task> task = taskDataBaseIntegration.find(uuid);
            
            if(!task.isPresent()){
                throw new TaskException("Task not found", HttpStatus.BAD_REQUEST);
            }
            
            return task.get();
            
        }catch(Throwable t){
            log.error("Error find task: ", t);
            throw new CustomException(t);
        }
    }
    
    public List<Task> listByTitle(String title){
        try{
            if(Objects.isNull(title)){
                List<Task> tasks = taskDataBaseIntegration.list();

                if(tasks.isEmpty()){
                    throw new TaskException("No tasks registered", HttpStatus.BAD_REQUEST);
                }

                return tasks;
            }
            
            Task taskFilter = new Task();
            taskFilter.setTitle(title);

            Example<Task> ex = Example.of(taskFilter);
            List<Task> tasks = taskDataBaseIntegration.list(ex);

            if(tasks.isEmpty()){
                throw new TaskException("No tasks registered with title "+ title, HttpStatus.BAD_REQUEST);
            }

            return tasks;

        }catch(Throwable t){
            log.error("Error list tasks by title: {}", t);
            throw new CustomException(t);
        }
    }
    
    public List<Task> listByUser(String userId){
        try{
            UUID uuid = UUID.fromString(userId);
            Optional<User> user = userDataBaseIntegration.find(uuid);
            if(!user.isPresent()){
                throw new TaskException("User not found", HttpStatus.BAD_REQUEST);
            }
            
            Task taskFilter = new Task();
            taskFilter.setUser(user.get());

            Example<Task> ex = Example.of(taskFilter);
            List<Task> tasks = taskDataBaseIntegration.list(ex);

            if(tasks.isEmpty()){
                throw new TaskException("No tasks registered by user "+ user.get().getUsername(), HttpStatus.BAD_REQUEST);
            }

            return tasks;

        }catch(Throwable t){
            log.error("Error list tasks by user: {}", t);
            throw new CustomException(t);
        }
    }
    
    public Task update(String id, Task task){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Task> opTask = taskDataBaseIntegration.find(uuid);
            
            if(!opTask.isPresent()){
                throw new TaskException("Task not found", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            
            Task t = opTask.get();
            if(!Objects.isNull(task.getTitle())){
                t.setTitle(task.getTitle());
            }
            
            if(!Objects.isNull(task.getDescription())){
                t.setDescription(task.getDescription());
            }
            
            if(!Objects.isNull(task.getStatus()) && !t.getStatus().equals(task.getStatus())){
                t.setStatus(task.getStatus());
                t.setModificationDate(LocalDateTime.now());
            }
            
            taskDataBaseIntegration.save(t);           
            return t;
        }
        catch(Throwable t){
            log.error("Error update task: {}", t);
            throw new CustomException(t);
        }
    }
    
    public void delete(final String id){
        try{
            UUID uuid = UUID.fromString(id);
            taskDataBaseIntegration.delete(uuid);
        }
        catch(Throwable t){
            throw new CustomException(t);
        }
    }
}
