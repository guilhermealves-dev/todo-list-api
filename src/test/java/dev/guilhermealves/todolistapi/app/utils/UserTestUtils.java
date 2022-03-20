/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.utils;

import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Guilherme
 */

public class UserTestUtils {
    
    public static User createUser(Role role){
        User user = new User();
        user.setIdUser(UUID.randomUUID());
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(role);
        return user;
    }
    
    public static List<User> createListUser(){
        return Arrays.asList(createUser(Role.USER));
    }
}
