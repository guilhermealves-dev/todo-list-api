package dev.guilhermealves.todolistapi.app.domain.model.error;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Guilherme
 */

@Getter
@Setter
public class FieldError {
    private String field;
    private String message;
    private String value;
}
