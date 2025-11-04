package com.example.lab4.service;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.model.Operators;
import com.example.lab4.repository.ApplicationRequestRepository;
import com.example.lab4.repository.OperatorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if (request.getId() == null) {
            request.setHandled(false);
        }
        requestRepository.save(request);
    }

    public boolean assignOperators(Long requestId, List<Long> operatorIds) {
        Optional<ApplicationRequest> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isEmpty()) return false;
        ApplicationRequest request = optionalRequest.get();
        List<Operators> operators = operatorsRepository.findAllById(operatorIds);
        request.setOperators(operators);
        request.setHandled(true);
        requestRepository.save(request);
        return true;
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}
