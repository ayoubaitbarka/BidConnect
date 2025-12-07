package com.example.tenderservice.service.evaluation;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SubmissionEvaluationResult {

    private Double scorePrice;
    private Double scoreTechnical;
    private Double scoreDelay;
    private Double finalScore;
}