package com.example.soumissionservice.services;

import com.example.soumissionservice.dto.SubmissionResponse;

public interface EvaluationService {
    SubmissionResponse evaluateSubmission(String submissionId);
}

