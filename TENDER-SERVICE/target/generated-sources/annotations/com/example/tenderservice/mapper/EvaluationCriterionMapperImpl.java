package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.EvaluationCriterionRequestDTO;
import com.example.tenderservice.dto.EvaluationCriterionResponseDTO;
import com.example.tenderservice.entity.EvaluationCriterion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-28T14:17:48+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class EvaluationCriterionMapperImpl implements EvaluationCriterionMapper {

    @Override
    public EvaluationCriterion toEntity(EvaluationCriterionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EvaluationCriterion.EvaluationCriterionBuilder evaluationCriterion = EvaluationCriterion.builder();

        evaluationCriterion.type( dto.getType() );
        evaluationCriterion.weight( dto.getWeight() );

        return evaluationCriterion.build();
    }

    @Override
    public EvaluationCriterionResponseDTO toResponseDTO(EvaluationCriterion entity) {
        if ( entity == null ) {
            return null;
        }

        EvaluationCriterionResponseDTO evaluationCriterionResponseDTO = new EvaluationCriterionResponseDTO();

        evaluationCriterionResponseDTO.setId( entity.getId() );
        evaluationCriterionResponseDTO.setType( entity.getType() );
        evaluationCriterionResponseDTO.setWeight( entity.getWeight() );

        return evaluationCriterionResponseDTO;
    }
}
