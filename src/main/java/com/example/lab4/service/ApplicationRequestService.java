package com.example.lab4.service;

import com.example.lab4.model.ApplicationRequest;
import com.example.lab4.model.Operators;
import com.example.lab4.repository.ApplicationRequestRepository;
import com.example.lab4.repository.OperatorsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationRequestService {

    private final ApplicationRequestRepository requestRepository;
    private final OperatorsRepository operatorsRepository;

    public void assignOperators(Long requestId, List<Long> operatorIds) {
        ApplicationRequest request = requestRepository.findById(requestId).orElseThrow();
        List<Operators> operators = operatorsRepository.findAllById(operatorIds);
        request.setOperators(operators);
        request.setHandled(true);
        requestRepository.save(request);
    }
}
