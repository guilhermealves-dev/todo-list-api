/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.domain.entities.core;

import dev.guilhermealves.todolistapi.domain.entities.Task;
import dev.guilhermealves.todolistapi.domain.entities.User;
import dev.guilhermealves.todolistapi.ports.out.DataBaseIntegration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class TaskCore {
    
    @Qualifier("task")
    private final DataBaseIntegration dataBaseIntegration;
    
    public Task create(Task task){
        try{                   
            task.setInclusionDate(LocalDateTime.now());
            Task newTask = (Task) dataBaseIntegration.save(task);
            return newTask;            
        }catch(Throwable t){
            log.error("Error create task: ", t);
            throw new RuntimeException(t);
        }
    }
    
    public Task find(String id){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Task> task = dataBaseIntegration.find(uuid);
            
            if(!task.isPresent()){
                throw new RuntimeException("Task not found");
            }
            
            return task.get();
            
        }catch(Throwable t){
            log.error("Error find task: ", t);
            throw new RuntimeException(t);
        }
    }
    
    public List<Task> list(User user){
        try{
            if(Objects.isNull(user)){
                List<Task> tasks = dataBaseIntegration.list();

                if(tasks.isEmpty()){
                    throw new RuntimeException("No tasks registered");
                }

                return tasks;
            }
            
            Task taskFilter = new Task();
            taskFilter.setUserId(user.getId());

            Example<Task> ex = Example.of(taskFilter);
            List<Task> tasks = dataBaseIntegration.list(ex);

            if(tasks.isEmpty()){
                throw new RuntimeException("No tasks registered by user "+ user.getUsername());
            }

            return tasks;

        }catch(Throwable t){
            log.error("Error list persons: {}", t);
            throw new RuntimeException(t);
        }     
    }
    
    public Task update(String id, Task task){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Task> opTask = dataBaseIntegration.find(uuid);
            
            if(!opTask.isPresent()){
                throw new RuntimeException("Task not found");
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
            
            dataBaseIntegration.save(t);           
            return t;
        }
        catch(Throwable t){
            throw new RuntimeException(t);
        }
    }
    
    public void delete(final String id){
        try{
            UUID uuid = UUID.fromString(id);
            dataBaseIntegration.delete(uuid);
        }
        catch(Throwable t){
            throw new RuntimeException(t);
        }
    }
}
