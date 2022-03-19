/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.ports.in;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Guilherme
 */

public interface TaskPort {
    public ResponseEntity<Task> create(Task task);
    public ResponseEntity<Task> find(String id);
    public ResponseEntity<List<Task>> list(String status);
    public ResponseEntity<List<Task>> listByUser(String userId);
    public ResponseEntity<Task> update(String id, Task task);
    public ResponseEntity<?> delete(String id);
}
