package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.*;
import com.example.tenderservice.entity.TenderDocumentRef;
import org.mapstruct.*;
import org.mapstruct.Mapping;
import org.springframework.web.bind.annotation.*;

@Mapper(componentModel = "spring")
public interface TenderDocumentRefMapper {

    TenderDocumentRef toEntity(TenderDocumentRefRequestDTO dto);

    @Mapping(
            target = "downloadUrl",
            expression = "java(\"http://localhost:8081/api/documents/\" + entity.getDocumentId() + \"/download\")"
    )

    TenderDocumentRefResponseDTO toResponseDTO(TenderDocumentRef entity);
}
