package dev.guilhermealves.todolistapi.app.domain.mapper;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Guilherme
 */

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({
            @Mapping(source = "u", target = "user"),
            @Mapping(target = "inclusionDate", expression = "java(inclusionDate())")
    })
    Task mapper(TaskModel task, User u);

    TaskModel mapper(Task task);

    List<TaskModel> mapper(List<Task> tasks);

    default LocalDateTime inclusionDate() {
        return LocalDateTime.now();
    }
}
