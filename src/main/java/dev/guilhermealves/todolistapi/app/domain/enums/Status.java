/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.enums;

/**
 *
 * @author Guilherme
 */

public enum Status {
    PENDING, COMPLETED;
    
    public static Status fromString(String text){
        for(Status status : Status.values()) {
            if(status.name().equalsIgnoreCase(text)){
                return status;
            }
        }
        return null;
    }
}
