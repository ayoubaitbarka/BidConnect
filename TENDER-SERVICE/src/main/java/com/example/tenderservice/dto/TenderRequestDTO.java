package com.example.tenderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TENDER_REQUEST_DTO", description = "Schema to hold TENDER information")
public class TenderRequestDTO {

    @Schema(description = "Project Title ", example = "Construction of New Bridge")
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Schema(description = "Project Description ", example = "Construction of New Bridge...")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Schema(description = "Organization ID from User-microservice", example = "215")
    @NotNull(message = "OrganizationId cannot be null")
    private Long organizationId;

    @Schema(description = "User ID from User-microservice", example = "1235468")
    @NotNull(message = "OwnerId cannot be null")
    private String ownerUserId;

    @Schema(description = "Deadline for tender", example = "2024-12-31")
    @NotNull(message = "Deadline cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @Schema(description = "Evaluation criteria list")
    @NotEmpty(message = "Criteria cannot be empty")
    private List<EvaluationCriterionRequestDTO> criteria;

    //@Schema(description = "Document metadata list (NOT the actual files)")
    //@NotEmpty(message = "Documents list cannot be empty")
    //private List<TenderDocumentRefRequestDTO> documents;
}