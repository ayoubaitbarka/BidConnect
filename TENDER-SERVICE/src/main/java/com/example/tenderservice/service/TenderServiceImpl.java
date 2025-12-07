package com.example.tenderservice.service;

import com.example.tenderservice.dto.*;
import com.example.tenderservice.entity.Tender;
import com.example.tenderservice.entity.TenderDocumentRef;
import com.example.tenderservice.entity.enumeration.TenderStatus;
import com.example.tenderservice.exception.ResourceNotFoundException;
import com.example.tenderservice.mapper.TenderMapper;
import com.example.tenderservice.repository.TenderRepository;
import com.example.tenderservice.service.ITenderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements ITenderService {

    private final TenderRepository tenderRepository;
    private final TenderMapper tenderMapper;

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
                        // ICI : vous pouvez enregistrer le fichier dans MinIO / AWS / disque
                        // puis récupérer l'id ou le chemin obtenu

                        return TenderDocumentRef.builder()
                                //.documentId(storedId)          // ID dans le storage
                                //.url(generatedUrl)             // URL d'accès au document
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
    public TenderResponseDTO updateTender(Long tenderId, TenderRequestDTO dto) {
        Tender tender = tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender", "id", tenderId.toString()));

        // MapStruct to update entity but maintains id
        Tender updated = tenderMapper.toEntity(dto);
        updated.setId(tender.getId());
        updated.setStatus(tender.getStatus());

        updated.setDocuments(tender.getDocuments()); // keep existing documents
        if (updated.getCriteria() != null) {
            updated.getCriteria().forEach(c -> c.setTender(updated));
        } // re-link criteria to updated tender

        tenderRepository.save(updated);
        return tenderMapper.toResponseDTO(updated);
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

