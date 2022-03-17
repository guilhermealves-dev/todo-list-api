/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Guilherme
 */

@Getter
@AllArgsConstructor
public class TaskException extends RuntimeException {
    
    private final String error;    
    private final HttpStatus status;
}
