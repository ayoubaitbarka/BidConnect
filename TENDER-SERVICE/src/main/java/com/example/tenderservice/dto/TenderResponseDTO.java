package com.example.tenderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenderResponseDTO {
    private Long id;
    private String title;
    private String description;

    private Long organizationId;
    private String ownerUserId;

    private String status;
    private LocalDate publicationDate;
    private LocalDate deadline;

    private List<EvaluationCriterionResponseDTO> criteria;
    private List<TenderDocumentRefResponseDTO> documents;
}
