package dev.guilhermealves.todolistapi.app.domain.entities;

import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.utils.TaskTestUtils;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.junit.jupiter.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 *
 * @author Guilherme
 */

public class TaskTest {

    @Test
    public void testEntity(){
        assertPojoMethodsFor(Task.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
    
    @Test
    public void mustCompare(){
        Task task1 = TaskTestUtils.createTask(Role.USER);
        Task task2 = TaskTestUtils.createTask(Role.ADMIN);        
        int compare = task1.compareTo(task2);
        assertTrue(compare == 0);
    }
}