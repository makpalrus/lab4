package com.example.lab4.api;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.service.ApplicationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class ApplicationRequestRestController {

    private final ApplicationRequestService requestService;

    @GetMapping
    public ResponseEntity<?> getAllRequests() {
        List<ApplicationRequest> requests = requestService.getAllRequests();
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRequestById(@PathVariable Long id) {
        Optional<ApplicationRequest> request = requestService.getRequestById(id);
        return request.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestBody ApplicationRequest request) {
        requestService.addRequest(request);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Long id, @RequestBody ApplicationRequest updated) {
        Optional<ApplicationRequest> existing = requestService.getRequestById(id);
        if (existing.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ApplicationRequest request = existing.get();
        request.setUserName(updated.getUserName());
        request.setCommentary(updated.getCommentary());
        request.setPhone(updated.getPhone());
        request.setHandled(updated.isHandled());
        request.setCourse(updated.getCourse());

        requestService.addRequest(request);
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Request deleted successfully");
    }
}
