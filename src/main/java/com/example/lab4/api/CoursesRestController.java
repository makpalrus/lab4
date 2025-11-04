package com.example.lab4.api;

import com.example.lab4.model.Courses;
import com.example.lab4.repository.CoursesRepository;
import com.example.lab4.service.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CoursesRestController {

    private final CoursesService coursesService;
    private final CoursesRepository coursesRepository;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<Courses> courses = coursesService.getAllCourses();
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Courses newCourse) {
        Courses saved = coursesRepository.save(newCourse);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        Courses course = coursesService.getCourseById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        coursesRepository.deleteById(id);
        return ResponseEntity.ok("Course deleted successfully");
    }
}
