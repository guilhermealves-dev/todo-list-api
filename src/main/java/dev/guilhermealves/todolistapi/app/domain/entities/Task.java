package dev.guilhermealves.todolistapi.app.domain.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Guilherme
 */

@Entity
@Getter
@Setter
public class Task implements Comparable<Task>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idTask;
    
    @ManyToOne
    @JoinColumn(name="idUser")
    private User user;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime inclusionDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modificationDate;
    
    private String title;
    
    @Size(min = 10, message = "Enter at least 10 characters")
    private String description;
    
    private Status status;

    @Override
    public int compareTo(Task o) {
        return this.status.compareTo(o.status);
    }
}
