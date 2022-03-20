/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.adapters.in;

import dev.guilhermealves.todolistapi.app.domain.core.TaskCore;
import dev.guilhermealves.todolistapi.app.domain.model.api.TaskModel;
import dev.guilhermealves.todolistapi.app.ports.in.TaskPort;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Guilherme
 */

@RestController
@RequestMapping("v1/tasks")
public class TaskControllerAdapter implements TaskPort {
    
    @Autowired
    private TaskCore core;

    @Override
    @PostMapping
    public ResponseEntity<TaskModel> create(@RequestBody @Valid TaskModel task) {
        TaskModel t = core.create(task);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> find(@PathVariable String id) {
        TaskModel t = core.find(id);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }
    
    @Override
    @GetMapping
    public ResponseEntity<List<TaskModel>> list(@RequestParam(required = false) String status) {
        List<TaskModel> t = core.list(status);
        return new ResponseEntity<>(t, HttpStatus.OK); 
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<TaskModel> update(@PathVariable String id, @RequestBody @Valid TaskModel task) {
        TaskModel t = core.update(id, task);
        return new ResponseEntity<>(t, HttpStatus.ACCEPTED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        core.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
}
