/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.config;

import dev.guilhermealves.todolistapi.app.domain.entities.User;
import dev.guilhermealves.todolistapi.app.domain.enums.Role;
import dev.guilhermealves.todolistapi.app.ports.out.DataBaseIntegration;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 *
 * @author Guilherme
 */

@EnableWebSecurity
@EnableAuthorizationServer
@EnableResourceServer
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Qualifier("user")
    private final DataBaseIntegration dataBaseIntegration;
    
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        
        List<User> users = dataBaseIntegration.list();
                
        for(User user : users){
            String role = user.getRole() == Role.ADMIN ? "ADMIN" : "USER";
            auth.inMemoryAuthentication().withUser(user.getUsername()).password(user.getPassword()).roles(role);
        }
        
//        auth.inMemoryAuthentication().withUser("guilherme").password("123").roles("ADMIN");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/h2/**");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
