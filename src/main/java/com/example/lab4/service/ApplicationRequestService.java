package com.example.lab4.service;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.model.Operators;
import com.example.lab4.repository.ApplicationRequestRepository;
import com.example.lab4.repository.OperatorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationRequestService {

    private final ApplicationRequestRepository requestRepository;
    private final OperatorsRepository operatorsRepository;

    public List<ApplicationRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<ApplicationRequest> getNewRequests() {
        return requestRepository.findByHandledFalse();
    }

    public List<ApplicationRequest> getHandledRequests() {
        return requestRepository.findByHandledTrue();
    }

    public Optional<ApplicationRequest> getRequestById(Long id) {
        return requestRepository.findById(id);
    }

    public void addRequest(ApplicationRequest request) {
        if (request.getOperators() == null) {
            request.setOperators(new ArrayList<>());
        }
        requestRepository.save(request);
    }

    @Transactional
    public ApplicationRequest assignOperatorToRequest(Long requestId, Long operatorId) {
        ApplicationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с ID " + requestId + " не найдена."));

        Operators operator = operatorsRepository.findById(operatorId)
                .orElseThrow(() -> new IllegalArgumentException("Оператор с ID " + operatorId + " не найден."));

        if (request.getOperators() == null) {
            request.setOperators(new ArrayList<>());
        }

        if (!request.getOperators().contains(operator)) {
            request.getOperators().add(operator);
        }

        request.setHandled(true);
        return requestRepository.save(request);
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}