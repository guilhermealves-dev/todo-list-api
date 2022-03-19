/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.guilhermealves.todolistapi.app.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.guilhermealves.todolistapi.app.domain.enums.Status;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Guilherme
 */

@Entity
@Getter
@Setter
//@JsonIgnoreProperties(value = {"user"})
public class Task implements Comparable<Task> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idTask;
    
    @ManyToOne
    @JoinColumn(name="idUser")
    private User user;
    
    private LocalDateTime inclusionDate;
    
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
