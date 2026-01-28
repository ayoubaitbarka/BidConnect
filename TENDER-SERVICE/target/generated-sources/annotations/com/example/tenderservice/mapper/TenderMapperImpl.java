package com.example.tenderservice.mapper;

import com.example.tenderservice.dto.EvaluationCriterionRequestDTO;
import com.example.tenderservice.dto.EvaluationCriterionResponseDTO;
import com.example.tenderservice.dto.TenderDocumentRefResponseDTO;
import com.example.tenderservice.dto.TenderRequestDTO;
import com.example.tenderservice.dto.TenderResponseDTO;
import com.example.tenderservice.entity.EvaluationCriterion;
import com.example.tenderservice.entity.Tender;
import com.example.tenderservice.entity.TenderDocumentRef;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T17:44:28+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class TenderMapperImpl implements TenderMapper {

    @Autowired
    private EvaluationCriterionMapper evaluationCriterionMapper;
    @Autowired
    private TenderDocumentRefMapper tenderDocumentRefMapper;

    @Override
    public Tender toEntity(TenderRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tender.TenderBuilder tender = Tender.builder();

        tender.title( dto.getTitle() );
        tender.description( dto.getDescription() );
        tender.organizationId( dto.getOrganizationId() );
        tender.ownerUserId( dto.getOwnerUserId() );
        tender.deadline( dto.getDeadline() );
        tender.criteria( evaluationCriterionRequestDTOListToEvaluationCriterionList( dto.getCriteria() ) );

        return tender.build();
    }

    @Override
    public TenderResponseDTO toResponseDTO(Tender entity) {
        if ( entity == null ) {
            return null;
        }

        TenderResponseDTO tenderResponseDTO = new TenderResponseDTO();

        tenderResponseDTO.setId( entity.getId() );
        tenderResponseDTO.setTitle( entity.getTitle() );
        tenderResponseDTO.setDescription( entity.getDescription() );
        tenderResponseDTO.setOrganizationId( entity.getOrganizationId() );
        tenderResponseDTO.setOwnerUserId( entity.getOwnerUserId() );
        if ( entity.getStatus() != null ) {
            tenderResponseDTO.setStatus( entity.getStatus().name() );
        }
        tenderResponseDTO.setPublicationDate( entity.getPublicationDate() );
        tenderResponseDTO.setDeadline( entity.getDeadline() );
        tenderResponseDTO.setCriteria( evaluationCriterionListToEvaluationCriterionResponseDTOList( entity.getCriteria() ) );
        tenderResponseDTO.setDocuments( tenderDocumentRefListToTenderDocumentRefResponseDTOList( entity.getDocuments() ) );

        return tenderResponseDTO;
    }

    @Override
    public List<TenderResponseDTO> toResponseDTOList(List<Tender> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TenderResponseDTO> list = new ArrayList<TenderResponseDTO>( entities.size() );
        for ( Tender tender : entities ) {
            list.add( toResponseDTO( tender ) );
        }

        return list;
    }

    protected List<EvaluationCriterion> evaluationCriterionRequestDTOListToEvaluationCriterionList(List<EvaluationCriterionRequestDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<EvaluationCriterion> list1 = new ArrayList<EvaluationCriterion>( list.size() );
        for ( EvaluationCriterionRequestDTO evaluationCriterionRequestDTO : list ) {
            list1.add( evaluationCriterionMapper.toEntity( evaluationCriterionRequestDTO ) );
        }

        return list1;
    }

    protected List<EvaluationCriterionResponseDTO> evaluationCriterionListToEvaluationCriterionResponseDTOList(List<EvaluationCriterion> list) {
        if ( list == null ) {
            return null;
        }

        List<EvaluationCriterionResponseDTO> list1 = new ArrayList<EvaluationCriterionResponseDTO>( list.size() );
        for ( EvaluationCriterion evaluationCriterion : list ) {
            list1.add( evaluationCriterionMapper.toResponseDTO( evaluationCriterion ) );
        }

        return list1;
    }

    protected List<TenderDocumentRefResponseDTO> tenderDocumentRefListToTenderDocumentRefResponseDTOList(List<TenderDocumentRef> list) {
        if ( list == null ) {
            return null;
        }

        List<TenderDocumentRefResponseDTO> list1 = new ArrayList<TenderDocumentRefResponseDTO>( list.size() );
        for ( TenderDocumentRef tenderDocumentRef : list ) {
            list1.add( tenderDocumentRefMapper.toResponseDTO( tenderDocumentRef ) );
        }

        return list1;
    }
}
