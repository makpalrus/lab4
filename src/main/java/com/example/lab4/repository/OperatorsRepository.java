package com.example.lab4.repository;

import com.example.lab4.model.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorsRepository extends JpaRepository<Operators, Long> {
}