/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.model.api;

import org.junit.jupiter.api.Test;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import pl.pojo.tester.api.assertion.Method;

/**
 *
 * @author Guilherme
 */

public class UserModelTest {
    
    @Test
    public void testModel(){
        assertPojoMethodsFor(UserModel.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
}
