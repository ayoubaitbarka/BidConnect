package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.TenderDocumentRefRequestDTO;
import com.example.tenderservice.dto.TenderDocumentRefResponseDTO;
import com.example.tenderservice.entity.TenderDocumentRef;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-06T22:40:42+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class TenderDocumentRefMapperImpl implements TenderDocumentRefMapper {

    @Override
    public TenderDocumentRef toEntity(TenderDocumentRefRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TenderDocumentRef.TenderDocumentRefBuilder tenderDocumentRef = TenderDocumentRef.builder();

        tenderDocumentRef.documentId( dto.getDocumentId() );
        tenderDocumentRef.fileName( dto.getFileName() );
        tenderDocumentRef.contentType( dto.getContentType() );

        return tenderDocumentRef.build();
    }

    @Override
    public TenderDocumentRefResponseDTO toResponseDTO(TenderDocumentRef entity) {
        if ( entity == null ) {
            return null;
        }

        TenderDocumentRefResponseDTO tenderDocumentRefResponseDTO = new TenderDocumentRefResponseDTO();

        tenderDocumentRefResponseDTO.setId( entity.getId() );
        tenderDocumentRefResponseDTO.setDocumentId( entity.getDocumentId() );
        tenderDocumentRefResponseDTO.setUrl( entity.getUrl() );
        tenderDocumentRefResponseDTO.setFileName( entity.getFileName() );
        tenderDocumentRefResponseDTO.setContentType( entity.getContentType() );

        return tenderDocumentRefResponseDTO;
    }
}
