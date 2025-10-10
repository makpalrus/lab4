package com.example.lab4.repository;

import com.example.lab4.model.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {

    List<ApplicationRequest> findByHandledFalse();

    List<ApplicationRequest> findByHandledTrue();
}
