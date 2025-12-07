package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.*;
import com.example.tenderservice.entity.Tender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                EvaluationCriterionMapper.class,
                TenderDocumentRefMapper.class
        }
)
public interface TenderMapper {

    // RequestDTO → Entity
    Tender toEntity(TenderRequestDTO dto);

    // Entity → ResponseDTO
    TenderResponseDTO toResponseDTO(Tender entity);

    List<TenderResponseDTO> toResponseDTOList(List<Tender> entities);
}