package dev.guilhermealves.todolistapi.app.domain.entities;

import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Guilherme
 */

@Entity
@Getter
@Setter
public class User implements Serializable{
    
    @Id
    private UUID idUser;
    
    private String username;
    
    private String password;
    
    private Role role;
    
}
