package com.example.soumissionservice.dto;

import com.example.soumissionservice.entity.SubmissionStatus;

public record StatusUpdateRequest(
        SubmissionStatus status
) {}

