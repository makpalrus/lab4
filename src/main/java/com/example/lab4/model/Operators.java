package com.example.lab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "operators")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "department")
    private String department;

    // We don't need to define the many-to-many relationship here
    // for this specific scenario as the 'requests' field is only needed
    // in ApplicationRequest to drive the application logic.
    // But for a complete M-to-M relationship, it would look like this:
    /*
    @ManyToMany(mappedBy = "operators")
    private Set<ApplicationRequest> requests;
    */

    public String getFullName() {
        return name + " " + surname;
    }
}