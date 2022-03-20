/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.repository.UserRepository;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guilherme
 */

@Service
@Qualifier("user")
public class UserDataBaseAdapter implements DataBaseIntegration<User, UUID> {
    
    @Autowired
    private UserRepository repository;

    @Override
    public User save(User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    @Cacheable(value = "user.id", key = "#id", unless="#result == null")
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findAll(Example<User> example) {
        return repository.findAll(example);
    }

    @Override
    public Optional<User> findOne(Example<User> example) {
        return repository.findOne(example);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
    
}
