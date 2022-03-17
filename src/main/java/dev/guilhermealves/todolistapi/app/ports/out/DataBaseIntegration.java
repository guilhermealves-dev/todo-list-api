/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.ports.out;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;

/**
 *
 * @author Guilherme
 */

public interface DataBaseIntegration<T, ID> {
    
    public T save(T t);
    public Optional<T> find(ID id);
    public List<T> list();
    public List<T> list(Example<T> example);
    public void delete(ID id);
}
