/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.domain.entities;

import dev.guilhermealves.todolistapi.domain.enums.Role;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String username;
    
    private String password;
    
    private Role role;
    
    public User(UUID id, String username, String password, Role role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public User(String username, String password, Role role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
