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
@Table(name = "application_requests")
public class ApplicationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", length = 200)
    private String userName;

    @Column(name = "commentary", columnDefinition = "TEXT")
    private String commentary;

    @Column(name = "phone", length = 100)
    private String phone;

    @Column(name = "handled")
    private boolean handled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Courses course;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "request_operators",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    @JsonIgnore
    private List<Operators> operators = new ArrayList<>();
}