package com.example.lab4.controller;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.model.Courses;
import com.example.lab4.service.ApplicationRequestService;
import com.example.lab4.service.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ApplicationRequestRestController {

    private final ApplicationRequestService requestService;
    private final CoursesService coursesService;

    @GetMapping
    public ResponseEntity<List<ApplicationRequest>> getAllRequests() {
        List<ApplicationRequest> requests = requestService.getAllRequests();
        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationRequest> getRequestById(@PathVariable Long id) {
        return requestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApplicationRequest> addRequest(@RequestBody ApplicationRequest request) {
        if (request.getCourse() != null && request.getCourse().getId() != null) {
            Courses course = coursesService.getCourseById(request.getCourse().getId());
            if (course == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            request.setCourse(course);
        } else {
            request.setCourse(null);
        }
        if (request.getOperators() == null) {
            request.setOperators(new ArrayList<>());
        }
        requestService.addRequest(request);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationRequest> updateRequest(@PathVariable Long id,
                                                            @RequestBody ApplicationRequest updated) {
        Optional<ApplicationRequest> existing = requestService.getRequestById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ApplicationRequest request = existing.get();
        request.setUserName(updated.getUserName());
        request.setPhone(updated.getPhone());
        request.setCommentary(updated.getCommentary());
        request.setHandled(updated.isHandled());

        if (updated.getCourse() != null && updated.getCourse().getId() != null) {
            Courses course = coursesService.getCourseById(updated.getCourse().getId());
            if (course == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            request.setCourse(course);
        } else if (updated.getCourse() != null && updated.getCourse().getId() == null) {
            request.setCourse(null);
        }

        if (request.getOperators() == null) {
            request.setOperators(new ArrayList<>());
        }
        requestService.addRequest(request);
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Заявка с ID " + id + " успешно удалена.");
    }
}