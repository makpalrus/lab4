package com.example.lab4.controller;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.model.Operators;
import com.example.lab4.service.ApplicationRequestService;
import com.example.lab4.service.OperatorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class OperatorsRestController {

    private final OperatorsService operatorsService;
    private final ApplicationRequestService applicationRequestService;

    @GetMapping
    public ResponseEntity<List<Operators>> getAllOperators() {
        List<Operators> operators = operatorsService.getAllOperators();
        if (operators.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(operators);
    }

    @PostMapping
    public ResponseEntity<Operators> addOperator(@RequestBody Operators operator) {
        operatorsService.saveOperator(operator);
        return new ResponseEntity<>(operator, HttpStatus.CREATED);
    }

    @PutMapping("/{operatorId}/assign/{requestId}")
    public ResponseEntity<ApplicationRequest> assignOperatorToRequest(
            @PathVariable Long operatorId, @PathVariable Long requestId) {
        try {
            ApplicationRequest updatedRequest = applicationRequestService.assignOperatorToRequest(requestId, operatorId);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}