package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.*;
import com.example.tenderservice.entity.TenderDocumentRef;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenderDocumentRefMapper {

    TenderDocumentRef toEntity(TenderDocumentRefRequestDTO dto);

    TenderDocumentRefResponseDTO toResponseDTO(TenderDocumentRef entity);
}