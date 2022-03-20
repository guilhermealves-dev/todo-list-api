package dev.guilhermealves.todolistapi;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 *
 * @author Guilherme
 */

@SpringBootApplication
@EnableCaching
@EnableAdminServer
public class TodoListApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListApiApplication.class, args);
    }
}
