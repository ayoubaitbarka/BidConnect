package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.*;
import com.example.tenderservice.entity.TenderDocumentRef;
import org.mapstruct.*;
import org.mapstruct.Mapping;
import org.springframework.web.bind.annotation.*;

@Mapper(componentModel = "spring")
public interface TenderDocumentRefMapper {

    //  ( ICI l'URL de téléchargement est construite à partir de l'ID du document)
    @Mapping(
            target = "downloadUrl",
            expression = "java(\"/api/documents/\" + entity.getDocumentId() + \"/download\")"
    )

    TenderDocumentRef toEntity(TenderDocumentRefRequestDTO dto);

    TenderDocumentRefResponseDTO toResponseDTO(TenderDocumentRef entity);
}