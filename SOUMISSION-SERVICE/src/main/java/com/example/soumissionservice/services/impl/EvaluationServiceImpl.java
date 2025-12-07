package com.example.soumissionservice.services.impl;

import com.example.soumissionservice.dto.SubmissionResponse;
import com.example.soumissionservice.entity.Submission;
import com.example.soumissionservice.entity.SubmissionStatus;
import com.example.soumissionservice.feignclients.TenderClient;
import com.example.soumissionservice.repository.SubmissionRepository;
import com.example.soumissionservice.util.ScoreCalculator;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final SubmissionRepository repo;
    private final TenderClient tenderClient;

    @Override
    public SubmissionResponse evaluateSubmission(String submissionId) {
        Submission sb = repo.findById(submissionId)
                .orElseThrow(() -> new NotFoundException("Submission not found"));

        var criteria = tenderClient.getCriteria(sb.getTenderId());

        ScoreCalculator.calculate(sb, criteria);

        sb.setStatus(SubmissionStatus.IN_EVALUATION);
        sb.setUpdatedAt(LocalDateTime.now());

        repo.save(sb);
        return new SubmissionResponse(sb.getId(), sb.getTenderId(),
                sb.getSupplierId(), sb.getStatus(), sb.getScore(), sb.getRagAnalysis());
    }
}

