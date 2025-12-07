package com.example.soumissionservice.dto;

import org.springframework.web.multipart.MultipartFile;

public record SubmissionRequest(
        String tenderId,
        String supplierId,
        MultipartFile Document
) {}

