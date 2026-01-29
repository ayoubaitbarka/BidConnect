package com.example.soumissionservice.dto;

import java.time.LocalDate;

public record TenderResponse(
        Long id,
        String status,
        LocalDate deadline) {
}
