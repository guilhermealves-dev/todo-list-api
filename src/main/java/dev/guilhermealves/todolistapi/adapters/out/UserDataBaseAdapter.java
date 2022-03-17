/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.adapters.out;

import dev.guilhermealves.todolistapi.domain.entities.User;
import dev.guilhermealves.todolistapi.domain.repository.UserRepository;
import dev.guilhermealves.todolistapi.ports.out.DataBaseIntegration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guilherme
 */

@Service
@Qualifier("user")
@RequiredArgsConstructor
public class UserDataBaseAdapter implements DataBaseIntegration<User, UUID> {
    
    private final UserRepository repository;

    @Override
    public User save(User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    public Optional<User> find(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> list(Example<User> example) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
