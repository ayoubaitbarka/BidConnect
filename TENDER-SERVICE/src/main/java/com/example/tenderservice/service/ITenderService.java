package com.example.tenderservice.service;

import com.example.tenderservice.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITenderService {

    TenderResponseDTO createTender(TenderRequestDTO dto,List<MultipartFile> files);

    TenderResponseDTO updateTender(Long tenderId, TenderRequestDTO dto);

    boolean deleteTender(Long tenderId);

    TenderResponseDTO getTenderById(Long tenderId);

    List<TenderResponseDTO> getAllTenders();

    List<TenderResponseDTO> getTendersByOrganization(Long organizationId);

    List<TenderResponseDTO> getTendersByOwnerUser(String ownerUserId);

    TenderResponseDTO publishTender(Long tenderId);

    TenderResponseDTO closeTender(Long tenderId);

}
