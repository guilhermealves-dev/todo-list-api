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
