package com.example.tenderservice.service.evaluation;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SubmissionEvaluationRequest {

    private Long tenderId;

    private Double price;

    private Double technicalScore;

    private Integer deliveryTimeDays;
}
