package com.example.tenderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.tenderservice.entity.enumeration.EvaluationCriterionType;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCriterionResponseDTO {
    private Long id;
    private EvaluationCriterionType type;
    private Integer weight;
}
