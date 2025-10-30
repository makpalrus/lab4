package com.example.lab4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operators")
public class Operators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "surname", length = 200)
    private String surname;

    @Column(name = "department", length = 200)
    private String department;

    @ManyToMany(mappedBy = "operators", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ApplicationRequest> requests = new ArrayList<>();
}