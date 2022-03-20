/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.model.api;

import dev.guilhermealves.todolistapi.app.utils.TaskTestUtils;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import pl.pojo.tester.api.assertion.Method;

/**
 *
 * @author Guilherme
 */

public class TaskModelTest {
    
    @Test
    public void testModel(){
        assertPojoMethodsFor(TaskModel.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
        TaskModel testModel = TaskTestUtils.createTaskModel();
        assertFalse(testModel.toString().isEmpty());
    }
}
