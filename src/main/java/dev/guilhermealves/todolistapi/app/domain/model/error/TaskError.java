package dev.guilhermealves.todolistapi.app.domain.model.error;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Guilherme
 */

@Getter
@Setter
public class TaskError {
    private Integer status;
    private String message;
}
