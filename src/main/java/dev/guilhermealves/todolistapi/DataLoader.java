/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi;

import dev.guilhermealves.todolistapi.domain.entities.User;
import dev.guilhermealves.todolistapi.domain.enums.Role;
import dev.guilhermealves.todolistapi.ports.out.DataBaseIntegration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Guilherme
 */

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    
    @Autowired
    @Qualifier("user")
    private final DataBaseIntegration dataBaseIntegration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        dataBaseIntegration.save(new User("guilherme", "123", Role.USER));
        dataBaseIntegration.save(new User("telma", "123", Role.ADMIN));
        dataBaseIntegration.save(new User("gabriel", "123", Role.ADMIN));
        
        //TODO: check how to put user static UUID
//        dataBaseIntegration.save(new User(UUID.fromString("fd7e69d2-9e99-4026-9eec-457ae3e55987"), "guilherme", "123", Role.USER));
//        dataBaseIntegration.save(new User(UUID.fromString("eb3129e6-c020-4bdc-9d87-0b2a341aeadb"), "telma", "123", Role.ADMIN));
//        dataBaseIntegration.save(new User(UUID.fromString("b26c801b-5710-490a-bc9b-062fee2f6fea"), "gabriel", "123", Role.ADMIN));
    }
    
}
