package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.*;
import com.example.tenderservice.entity.EvaluationCriterion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluationCriterionMapper {

    EvaluationCriterion toEntity(EvaluationCriterionRequestDTO dto);

    EvaluationCriterionResponseDTO toResponseDTO(EvaluationCriterion entity);
}
