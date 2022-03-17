/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.repository.TaskRepository;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guilherme
 */

@Service
@Primary
@Qualifier("task")
@RequiredArgsConstructor
public class TaskDataBaseAdapter implements DataBaseIntegration<Task, UUID> {

    private final TaskRepository repository;

    @Override
    public Task save(Task t) {
        return repository.saveAndFlush(t);
    }

    @Override
    public Optional<Task> find(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> list() {
        return repository.findAll();
    }

    @Override
    public List<Task> list(Example<Task> example) {
        return repository.findAll(example);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
    
}
