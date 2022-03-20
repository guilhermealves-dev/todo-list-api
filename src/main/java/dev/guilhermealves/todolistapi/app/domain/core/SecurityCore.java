package dev.guilhermealves.todolistapi.app.domain.core;

import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.exception.CustomException;
import dev.guilhermealves.todolistapi.app.domain.exception.TaskException;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author Guilherme
 */

@Service
public class SecurityCore {

    @Autowired
    @Qualifier("user")
    private DataBaseIntegration userDataBaseIntegration;

    public User getCurrentUser() {
      try {
          Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          String username = ((UserDetails)principal).getUsername();

          User userFilter = new User();
          userFilter.setUsername(username);

          Example<User> ex = Example.of(userFilter);

          Optional<User> user = userDataBaseIntegration.findOne(ex);

          if(!user.isPresent()){
              throw new TaskException("User not found", HttpStatus.BAD_REQUEST);
          }

          return user.get();
      }catch (Throwable t) {
          throw new CustomException(t);
      }
    }
}
