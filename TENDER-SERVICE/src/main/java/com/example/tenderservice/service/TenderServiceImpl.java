package com.example.tenderservice.service;

import com.example.tenderservice.dto.*;
import com.example.tenderservice.entity.EvaluationCriterion;
import com.example.tenderservice.entity.Tender;
import com.example.tenderservice.entity.TenderDocumentRef;
import com.example.tenderservice.entity.enumeration.TenderStatus;
import com.example.tenderservice.exception.ResourceNotFoundException;
import com.example.tenderservice.feignclients.DocumentClient;
import com.example.tenderservice.mapper.TenderMapper;
import com.example.tenderservice.repository.TenderRepository;
import com.example.tenderservice.service.ITenderService;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements ITenderService {

    private final TenderRepository tenderRepository;
    private final TenderMapper tenderMapper;
    private final DocumentClient documentClient;

    @Override
    public TenderResponseDTO createTender(TenderRequestDTO dto,List<MultipartFile> files) {
        Tender tender = tenderMapper.toEntity(dto);
        tender.setStatus(TenderStatus.DRAFT);
        tender.setPublicationDate(LocalDate.now());

        //  LIAISON CORRECTE DES CRITERIA
        if (tender.getCriteria() != null) {
            tender.getCriteria().forEach(c -> c.setTender(tender));
        }

        // --- LIAISON DES DOCUMENTS ---
        if (files != null && !files.isEmpty()) {

            List<TenderDocumentRef> docRefs = files.stream()
                    .map(file -> {

//                        TenderService n’upload pas de fichiers
//                        TenderService appelle Document-Service
//                        TenderService stocke documentId
//                        L’URL est calculée dans les DTO de réponse, pas en base

                        String documentId = documentClient.upload(file);
                        return TenderDocumentRef.builder()
                                .documentId(documentId)          // ID dans le storage
                                .fileName(file.getOriginalFilename())
                                .contentType(file.getContentType())
                                .tender(tender)                // relation many-to-one
                                .build();
                    })
                    .toList();

            tender.setDocuments(docRefs);
        }


        tenderRepository.save(tender);

        return tenderMapper.toResponseDTO(tender);
    }

    @Override
    @Transactional
    public TenderResponseDTO updateTender(Long tenderId, TenderRequestDTO dto) {

        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tender", "id", tenderId.toString()));

        // --- Mise à jour simple des champs : car j'ai trouvé un prob de synchronisation ---
        tender.setTitle(dto.getTitle());
        tender.setDescription(dto.getDescription());
        tender.setDeadline(dto.getDeadline());
        tender.setOrganizationId(dto.getOrganizationId());
        tender.setOwnerUserId(dto.getOwnerUserId());


        // --- MISE À JOUR DES CRITÈRES ---

        List<EvaluationCriterion> existing = tender.getCriteria();           // base
        @NotEmpty(message = "Criteria cannot be empty") List<EvaluationCriterionRequestDTO> incoming = dto.getCriteria();              // new DTO

        for (EvaluationCriterionRequestDTO newC : incoming) {

            EvaluationCriterion old = existing.stream()
                    .filter(c -> c.getType().equals(newC.getType()))
                    .findFirst()
                    .orElse(null);

            if (old != null) {
                old.setWeight(newC.getWeight());
            }
        }


        // NOTE : on ne touche pas aux documents ici
        // ils peuvent être mis à jour dans un endpoint séparé

        Tender saved = tenderRepository.save(tender);

        return tenderMapper.toResponseDTO(saved);
    }


    @Override
    public boolean deleteTender(Long tenderId) {
        if (!tenderRepository.existsById(tenderId))
            return false;

        tenderRepository.deleteById(tenderId);
        return true;
    }

    @Override
    public TenderResponseDTO getTenderById(Long tenderId) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender", "id", tenderId.toString()));

        return tenderMapper.toResponseDTO(tender);
    }

    @Override
    public List<TenderResponseDTO> getAllTenders() {
        return tenderMapper.toResponseDTOList(tenderRepository.findAll());
    }

    @Override
    public List<TenderResponseDTO> getTendersByOrganization(Long organizationId) {
        return tenderMapper.toResponseDTOList(
                tenderRepository.findByOrganizationId(organizationId)
        );
    }

    @Override
    public List<TenderResponseDTO> getTendersByOwnerUser(String ownerUserId) {
        return tenderMapper.toResponseDTOList(
                tenderRepository.findByOwnerUserId(ownerUserId)
        );
    }

    @Override
    public TenderResponseDTO publishTender(Long tenderId) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender", "id", tenderId.toString()));

        tender.setStatus(TenderStatus.PUBLISHED);
        tenderRepository.save(tender);

        return tenderMapper.toResponseDTO(tender);
    }

    @Override
    public TenderResponseDTO closeTender(Long tenderId) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender", "id", tenderId.toString()));

        tender.setStatus(TenderStatus.CLOSED);
        tenderRepository.save(tender);

        return tenderMapper.toResponseDTO(tender);
    }
}

