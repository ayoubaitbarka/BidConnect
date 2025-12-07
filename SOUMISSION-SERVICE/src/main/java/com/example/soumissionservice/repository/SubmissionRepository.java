package com.example.soumissionservice.repository;

import com.example.soumissionservice.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, String> {
    List<Submission> findByTenderId(String tenderId);
    List<Submission> findBySupplierId(String supplierId);
}
