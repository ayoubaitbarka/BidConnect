package com.example.tenderservice.dto;

import com.example.tenderservice.entity.enumeration.EvaluationCriterionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationCriterionRequestDTO {
    private EvaluationCriterionType type;
    private Integer weight;
}