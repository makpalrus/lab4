package com.example.lab4.service;

import com.example.lab4.model.Courses;
import com.example.lab4.repository.CoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesService {

    private final CoursesRepository coursesRepository;

    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    public Courses getCourseById(Long id) {
        return coursesRepository.findById(id).orElse(null);
    }

    public void deleteCourse(Long id) {
        coursesRepository.deleteById(id);
    }
}
