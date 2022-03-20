package dev.guilhermealves.todolistapi.app.adapters.out;

import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.domain.repository.UserRepository;
import dev.guilhermealves.todolistapi.app.utils.UserTestUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Example;


/**
 *
 * @author Guilherme
 */

@ExtendWith(MockitoExtension.class)
public class UserDataBaseAdapterTest {

    @InjectMocks
    private UserDataBaseAdapter userDataBaseAdapter;

    @Mock
    private UserRepository repository;

    @Test
    public void mustCreate(){
        when(repository.saveAndFlush(any(User.class))).thenReturn(UserTestUtils.createUser(Role.USER));
        User user = userDataBaseAdapter.save(UserTestUtils.createUser(Role.USER));
        assertNotNull(user);
    }
    
    @Test
    public void mustFindById(){
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(UserTestUtils.createUser(Role.USER)));
        Optional<User> opUser = userDataBaseAdapter.findById(UUID.randomUUID());
        assertTrue(opUser.isPresent());
    }

    @Test
    public void mustFindAll(){
        when(repository.findAll()).thenReturn(UserTestUtils.createListUser());
        List<User> users = userDataBaseAdapter.findAll();
        assertFalse(users.isEmpty());
    }
    
    @Test
    public void mustFindAllExample(){
        when(repository.findAll(any(Example.class))).thenReturn(UserTestUtils.createListUser());
        List<User> users = userDataBaseAdapter.findAll(Example.of(UserTestUtils.createUser(Role.USER)));
        assertFalse(users.isEmpty());
    }
    
    @Test
    public void mustFindOne(){
        when(repository.findOne(any(Example.class))).thenReturn(Optional.of(UserTestUtils.createUser(Role.USER)));
        Optional<User> opUser = userDataBaseAdapter.findOne(Example.of(UserTestUtils.createUser(Role.USER)));
        assertTrue(opUser.isPresent());
    }
    
    @Test
    public void mustDelete(){
        userDataBaseAdapter.deleteById(UUID.randomUUID());
    }
}