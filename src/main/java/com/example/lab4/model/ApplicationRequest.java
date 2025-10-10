package com.example.lab4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "application_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "commentary", length = 500)
    private String commentary;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "handled", nullable = false)
    private Boolean handled = false;
}
