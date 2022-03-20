package dev.guilhermealves.todolistapi.app.domain.model.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 *
 * @author Guilherme
 */

@Getter
@Setter
@ToString
public class UserModel {

    private UUID idUser;
}
