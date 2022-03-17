/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Guilherme
 */

@Getter
@Setter
public class FieldError {
    private String field;
    private String message;
    private String value;
}
