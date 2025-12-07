package com.example.soumissionservice.services;

import com.example.soumissionservice.dto.SubmissionRequest;
import com.example.soumissionservice.dto.SubmissionResponse;
import com.example.soumissionservice.entity.SubmissionStatus;

import java.util.List;

public interface SubmissionService {
    SubmissionResponse createSubmission(SubmissionRequest request);
    SubmissionResponse findSubmission(String id);
    List<SubmissionResponse> getByTender(String tenderId);
    void updateStatus(String id, SubmissionStatus status);
}
