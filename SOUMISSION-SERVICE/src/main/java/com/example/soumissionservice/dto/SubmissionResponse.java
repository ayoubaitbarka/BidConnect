package com.example.soumissionservice.dto;

import com.example.soumissionservice.entity.SubmissionStatus;

public record SubmissionResponse(
        String id,
        String tenderId,
        String supplierId,
        SubmissionStatus status,
        double scoreTotal,
        String ragAnalysis
) {}

