package dev.guilhermealves.todolistapi.app.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Guilherme
 */

public class StatusTest {
    
    @Test
    public void mustFind(){
        assertNotNull(Status.fromString("pending"));
        assertNotNull(Status.fromString("completed"));        
        assertNotNull(Status.fromString("COMPLETED"));
    }
    
    @Test
    public void mustNotFind(){
        assertNull(Status.fromString("test"));
    }
}
