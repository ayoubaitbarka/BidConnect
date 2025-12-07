package com.example.soumissionservice.services.impl;

import com.example.soumissionservice.dto.SubmissionRequest;
import com.example.soumissionservice.dto.SubmissionResponse;
import com.example.soumissionservice.entity.Submission;
import com.example.soumissionservice.entity.SubmissionStatus;
import com.example.soumissionservice.feignclients.*;
import com.example.soumissionservice.mapper.SubmissionMapper;
import com.example.soumissionservice.repository.SubmissionRepository;
import com.example.soumissionservice.services.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository repo;
    private final TenderClient tenderClient;
    private final DocumentClient documentClient;
    private final AIClient aiClient;
//    private final UserClient userClient;
//    private final NotificationClient notificationClient;
    private final SubmissionMapper subMapper;

    @Override
    public SubmissionResponse createSubmission(SubmissionRequest req) {

        // Vérifie si l’appel d'offre est ouvert
        tenderClient.validateTenderOpen(req.tenderId());

        // Vérifie l’existence du supplier
//        userClient.getUser(req.supplierId());

        // Upload documents
        String Url = documentClient.upload(req.Document());


        Submission s = new Submission();
        s.setTenderId(req.tenderId());
        s.setSupplierId(req.supplierId());
        s.setDocUrl(Url);
        s.setStatus(SubmissionStatus.SUBMITTED);
        s.setCreatedAt(LocalDateTime.now());

        repo.save(s);

        // Analyse IA
        String ragResult = aiClient.analyze(s.getId());
        s.setRagAnalysis(ragResult);

        repo.save(s);

        // Notification Owner
//        notificationClient.notifySubmission(
//                new NotificationRequest(req.tenderId(), req.supplierId(), "New submission received")
//        );

        return subMapper.toResponse(s);
    }

    @Override
    public SubmissionResponse findSubmission(String id) {
        Submission s = repo.findById(id).orElse(null);
        return subMapper.toResponse(s);
    }

    @Override
    public List<SubmissionResponse> getByTender(String tenderId) {
        List<Submission> submissions = repo.findByTenderId(tenderId);
        List<SubmissionResponse> responses = new ArrayList<>();
        for (Submission s : submissions) {
            responses.add(subMapper.toResponse(s));
        }
        return responses;
    }


    @Override
    public void updateStatus(String id, SubmissionStatus status) {

        Submission s = repo.findById(id).orElse(null);
        s.setStatus(status);
        repo.save(s);
    }

}
