/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.ports.in;

import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 *
 * @author Guilherme
 */

public interface TaskPort {
    public ResponseEntity<TaskModel> create(TaskModel TaskModel);
    public ResponseEntity<TaskModel> find(String id);
    public ResponseEntity<List<TaskModel>> list(String status);
    public ResponseEntity<TaskModel> update(String id, TaskModel TaskModel);
    public ResponseEntity<?> delete(String id);
}
