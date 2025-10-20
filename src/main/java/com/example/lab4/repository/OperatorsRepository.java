package com.example.lab4.repository;

import com.example.lab4.model.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operators, Long> {
    // Здесь также можно добавить кастомные методы, если понадобятся, например, поиск по отделу
}