package com.example.lab4.api;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.model.Operators;
import com.example.lab4.service.ApplicationRequestService;
import com.example.lab4.service.OperatorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operators")
public class OperatorsRestController {

    private final OperatorsService operatorsService;
    private final ApplicationRequestService requestService;

    @GetMapping
    public ResponseEntity<?> getAllOperators() {
        List<Operators> operators = operatorsService.getAllOperators();
        if (operators.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(operators);
    }

    @PostMapping
    public ResponseEntity<?> addOperator(@RequestBody Operators operator) {
        operatorsService.saveOperator(operator);
        return new ResponseEntity<>(operator, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/assign/{requestId}")
    public ResponseEntity<?> assignOperatorToRequest(@PathVariable Long id, @PathVariable Long requestId) {
        boolean success = requestService.assignOperators(requestId, List.of(id));
        if (!success) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<ApplicationRequest> updated = requestService.getRequestById(requestId);
        return ResponseEntity.ok(updated.get());
    }
}
