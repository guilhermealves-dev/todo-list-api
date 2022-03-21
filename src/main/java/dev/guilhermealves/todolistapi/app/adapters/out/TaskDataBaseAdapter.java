package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import dev.guilhermealves.todolistapi.app.domain.repository.TaskRepository;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Guilherme
 */

@Service
@Primary
@Qualifier("task")
public class TaskDataBaseAdapter implements DataBaseIntegration<Task, UUID> {

    @Autowired
    private TaskRepository repository;

    @Override
    public Task save(Task t) {
        return repository.saveAndFlush(t);
    }

    @Override
    @Cacheable(value = "task.id", key = "#id", unless="#result == null")
    public Optional<Task> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Task> findAll(Example<Task> example) {
        return repository.findAll(example);
    }

    @Override
    public Optional<Task> findOne(Example<Task> example) {
        return repository.findOne(example);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
    
}
