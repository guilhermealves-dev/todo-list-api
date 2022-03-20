package dev.guilhermealves.todolistapi.app.domain.entities;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 *
 * @author Guilherme
 */

public class UserTest {

    @Test
    public void testEntity(){
        assertPojoMethodsFor(User.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
}