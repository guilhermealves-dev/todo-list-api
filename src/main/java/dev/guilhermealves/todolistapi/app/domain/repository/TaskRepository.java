package dev.guilhermealves.todolistapi.app.domain.repository;

import dev.guilhermealves.todolistapi.app.domain.entities.Task;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Guilherme
 */

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, UUID> {
    
}
