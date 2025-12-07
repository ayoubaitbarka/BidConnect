package com.example.soumissionservice.dto;

public record EvaluationRequest(
        double technicalScore,
        double price,
        double deadline
) {}

