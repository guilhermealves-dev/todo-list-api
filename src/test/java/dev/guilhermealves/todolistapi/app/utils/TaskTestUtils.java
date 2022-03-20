package dev.guilhermealves.todolistapi.app.utils;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import dev.guilhermealves.todolistapi.app.domain.model.api.UserModel;
import static dev.guilhermealves.todolistapi.app.utils.UserTestUtils.createUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Guilherme
 */

public class TaskTestUtils {

    public static TaskModel createTaskModel() {
        TaskModel taskModel = new TaskModel();
        taskModel.setIdTask(UUID.randomUUID());
        taskModel.setDescription("test");
        taskModel.setInclusionDate(LocalDateTime.now());
        taskModel.setTitle("test");
        taskModel.setStatus(Status.PENDING);

        UserModel userModel = new UserModel();
        userModel.setIdUser(UUID.randomUUID());

        taskModel.setUser(userModel);

        return taskModel;
    }

    public static List<TaskModel> createListTaskModel() {
        return Arrays.asList(createTaskModel());
    }

    public static Task createTask(Role role){
        Task task = new Task();
        task.setIdTask(UUID.randomUUID());
        task.setDescription("test");
        task.setInclusionDate(LocalDateTime.now());
        task.setTitle("test");
        task.setStatus(Status.PENDING);
        task.setUser(createUser(role));

        return task;
    }
    
    public static List<Task> createListTask(){
        return Arrays.asList(createTask(Role.USER));
    }    
}
