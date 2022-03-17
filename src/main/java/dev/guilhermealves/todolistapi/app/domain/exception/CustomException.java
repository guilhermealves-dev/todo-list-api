/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.exception;

/**
 *
 * @author Guilherme
 */
public class CustomException extends RuntimeException {
    
    public CustomException(final Throwable t){
        super(t);
    }
}
