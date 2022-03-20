/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Guilherme
 */

@Getter
public class TaskException extends RuntimeException {

    private HttpStatus status;

    public TaskException(String error, HttpStatus status){
        super(error);
        this.status = status;
    }
}
