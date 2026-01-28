package com.example.soumissionservice.dto;

import com.example.soumissionservice.entity.SubmissionStatus;

public record SubmissionResponse(
        String id,
        String tenderId,
        String supplierId,
        String documentId,
        SubmissionStatus status,
        Double price,
        Double technical,
        Double deadline,
        Double score,
        String ragAnalysis
) {}

