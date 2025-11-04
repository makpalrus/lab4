package com.example.lab4.service;

import com.example.lab4.model.Operators;
import com.example.lab4.repository.OperatorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorsService {

    private final OperatorsRepository operatorsRepository;

    public List<Operators> getAllOperators() {
        return operatorsRepository.findAll();
    }

    public Operators getOperatorById(Long id) {
        return operatorsRepository.findById(id).orElse(null);
    }

    public void saveOperator(Operators operator) {
        operatorsRepository.save(operator);
    }

    public void deleteOperator(Long id) {
        operatorsRepository.deleteById(id);
    }
}
