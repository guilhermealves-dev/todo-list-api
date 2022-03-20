package dev.guilhermealves.todolistapi.app.domain.model.api;

import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Guilherme
 */

@Getter
@Setter
@ToString
public class TaskModel {

    private UUID idTask;

    private UserModel user;

    private LocalDateTime inclusionDate;
    private LocalDateTime modificationDate;

    @NotEmpty
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Enter at least 10 characters")
    private String description;

    @NotNull
    private Status status;
}
