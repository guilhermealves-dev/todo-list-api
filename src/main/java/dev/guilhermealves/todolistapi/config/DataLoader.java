/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.config;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
    
    @Qualifier("user")
    private final DataBaseIntegration dataBaseIntegration;
    
    @Qualifier("task")
    private final DataBaseIntegration taskDataBaseIntegration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
                 
        User user1 = new User();
        user1.setUsername("guilherme");
        user1.setPassword("123");
        user1.setRole(Role.USER);
        user1.setIdUser(UUID.fromString("f4eff9cf-e496-427c-9629-bd35edaa2190"));
        dataBaseIntegration.save(user1);
        
        User user2 = new User();
        user2.setUsername("telma");
        user2.setPassword("123");
        user2.setRole(Role.ADMIN);
        user2.setIdUser(UUID.fromString("bac239f8-4d1a-4f00-b737-36451de7cc84"));
        dataBaseIntegration.save(user2);
        
        User user3 = new User();
        user3.setUsername("gabriel");
        user3.setPassword("123");
        user3.setRole(Role.ADMIN);
        user3.setIdUser(UUID.fromString("203b6558-ed6e-4360-965f-418264abc58f"));
        dataBaseIntegration.save(user3);     

        createTaskTest();
    }
    
    private void createTaskTest(){
        List<User> users = dataBaseIntegration.list();
        
        Task t1 = new Task();
        t1.setUser(users.get(0));
        t1.setTitle("Comprar leite");
        t1.setDescription("Comprar leita desnatado para a dieta");
        t1.setStatus(Status.PENDING);
        t1.setInclusionDate(LocalDateTime.now());
                
        taskDataBaseIntegration.save(t1);
        
        Task t2 = new Task();
        t2.setUser(users.get(0));
        t2.setTitle("Comprar frutas");
        t2.setDescription("Comprar frutas na feira para a dieta");
        t2.setStatus(Status.COMPLETED);
        t2.setInclusionDate(LocalDateTime.now().minusDays(2));
        t2.setModificationDate(LocalDateTime.now().minusDays(1));
        taskDataBaseIntegration.save(t2);
        
        Task t3 = new Task();
        t3.setUser(users.get(1));
        t3.setTitle("Abastecer");
        t3.setDescription("Lembrar de abastecer 50 reais de gasolina no carro");
        t3.setStatus(Status.PENDING);
        t3.setInclusionDate(LocalDateTime.now());
        taskDataBaseIntegration.save(t3);
    }
}