package com.example.lab4.controller;

import com.example.lab4.model.Courses;
import com.example.lab4.service.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CoursesRestController {

    private final CoursesService coursesService;

    @GetMapping
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = coursesService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<Courses> addCourse(@RequestBody Courses course) {
        Courses saved = coursesService.saveCourse(course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        Courses course = coursesService.getCourseById(id);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Курс с ID " + id + " не найден.");
        }
        coursesService.deleteCourse(id);
        return ResponseEntity.ok("Курс с ID " + id + " успешно удален.");
    }
}