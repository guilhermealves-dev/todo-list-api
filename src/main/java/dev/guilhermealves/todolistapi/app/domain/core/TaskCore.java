/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.core;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.exception.TaskException;
import dev.guilhermealves.todolistapi.app.domain.mapper.TaskMapper;
import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
    private SecurityCore securityCore;

    @Autowired
    private TaskMapper taskMapper;

    public TaskModel create(TaskModel taskModel){
        try{
            User user = securityCore.getCurrentUser();

            Task task = taskMapper.mapper(taskModel, user);

            Task newTask = (Task) taskDataBaseIntegration.save(task);

            TaskModel model = taskMapper.mapper(newTask);

            log.info("task created successfully - {}", model);

            return model;

        }catch(Throwable t){
            log.error("Error create taskModel: ", t);
            throw new CustomException(t);
        }
    }

    public TaskModel find(String id){
        try{
            UUID uuid = UUID.fromString(id);
            Optional<Task> opTask = taskDataBaseIntegration.findById(uuid);

            if(!opTask.isPresent()){
                throw new TaskException("Task not found", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            User user = securityCore.getCurrentUser();

            if(user.getRole().equals(Role.ADMIN)){
                TaskModel model = taskMapper.mapper(opTask.get());
                log.info("opTask found successfully - {}", model);
                
                return model;
            }

            if (!opTask.get().getUser().getIdUser().equals(user.getIdUser())){
                throw new TaskException("Not authorized", HttpStatus.FORBIDDEN);
            }

            TaskModel model = taskMapper.mapper(opTask.get());

            log.info("opTask found successfully - {}", model);
            
            return model;
            
        }catch(Throwable t){
            log.error("Error find task: ", t);
            throw new CustomException(t);
        }
    }

    public List<TaskModel> list(String status){
        try{
            User user = securityCore.getCurrentUser();
            List<Task> tasks;

            if(Objects.isNull(status)){
                if(user.getRole().equals(Role.ADMIN)){
                    tasks = taskDataBaseIntegration.findAll();
                } 
                else{
                    Task taskFilter = new Task();
                    taskFilter.setUser(user);
                    Example<Task> ex = Example.of(taskFilter);
                    tasks = taskDataBaseIntegration.findAll(ex);
                }

                if(Objects.isNull(tasks) || tasks.isEmpty()){
                    throw new TaskException("No tasks registered", HttpStatus.UNPROCESSABLE_ENTITY);
                }

                Collections.sort(tasks);
                List<TaskModel> models = taskMapper.mapper(tasks);

                log.info("tasks listed successfully -{}", models);

                return models;
            }

            Status statusFilter = Status.fromString(status);
            if(Objects.isNull(statusFilter)){
                throw new TaskException("Status not found", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            Task taskFilter = new Task();
            taskFilter.setStatus(statusFilter);

            if(user.getRole().equals(Role.ADMIN)){
                Example<Task> ex = Example.of(taskFilter);

                tasks = taskDataBaseIntegration.findAll(ex);

                if(Objects.isNull(tasks) || tasks.isEmpty()){
                    throw new TaskException("No tasks registered with status ".concat(status.toString()), HttpStatus.BAD_REQUEST);
                }

                Collections.sort(tasks);
                List<TaskModel> models = taskMapper.mapper(tasks);

                log.info("tasks listed successfully -{}", models);

                return models;
            }

            taskFilter.setUser(user);

            Example<Task> ex = Example.of(taskFilter);
            tasks = taskDataBaseIntegration.findAll(ex);

            if(Objects.isNull(tasks) || tasks.isEmpty()){
                throw new TaskException("No tasks registered with status ".concat(status.toString()),
                        HttpStatus.BAD_REQUEST);
            }

            List<TaskModel> models = taskMapper.mapper(tasks);

            log.info("tasks listed successfully -{}", models);

            return models;

        }catch(Throwable t){
            log.error("Error list tasks by status: {}", t);
            throw new CustomException(t);
        }
    }

    public TaskModel update(String id, TaskModel task){
        try{
            User user = securityCore.getCurrentUser();
            UUID uuid = UUID.fromString(id);
            Optional<Task> opTask = taskDataBaseIntegration.findById(uuid);

            if(!opTask.isPresent()){
                throw new TaskException("Task not found", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            
            if(user.getRole().equals(Role.USER) && !opTask.get().getUser().getIdUser().equals(user.getIdUser())){
                throw new TaskException("Not authorized", HttpStatus.FORBIDDEN);
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

            TaskModel model = taskMapper.mapper(taskUpdate);
            log.info("task updated successfully - {}", model);

            return model;
        }
        catch(Throwable t){
            log.error("Error update task: {}", t);
            throw new CustomException(t);
        }
    }

    public void delete(final String id){
        try{
            User user = securityCore.getCurrentUser();
            UUID uuid = UUID.fromString(id);            
            Optional<Task> opTask = taskDataBaseIntegration.findById(uuid);

            if(!opTask.isPresent()){
                throw new TaskException("Task not found", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            if(user.getRole().equals(Role.USER) && !opTask.get().getUser().getIdUser().equals(user.getIdUser())){
                throw new TaskException("Not authorized", HttpStatus.FORBIDDEN);
            }

            taskDataBaseIntegration.deleteById(uuid);
            log.info("task deleted successfully");
        }
        catch(Throwable t){
            log.error("Error delete tasks by id: {}", t);
            throw new CustomException(t);
        }
    }
}
