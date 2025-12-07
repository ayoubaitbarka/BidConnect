package com.example.tenderservice.service.evaluation;

import com.example.tenderservice.entity.EvaluationCriterion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreCalculatorService {

    public SubmissionEvaluationResult calculateScore(
            SubmissionEvaluationRequest submission,
            List<EvaluationCriterion> criteria,
            double minPriceAmongAllSubmissions
    ) {
        double scorePrice = 0;
        double scoreTechnical = 0;
        double scoreDelay = 0;

        for (EvaluationCriterion c : criteria) {
            double weight = c.getWeight() / 100.0;

            switch (c.getType()) {
                case PRICE -> {
                    double prixRatio = minPriceAmongAllSubmissions / submission.getPrice();
                    scorePrice = prixRatio * 100 * weight;
                }
                case TECHNICAL_QUALITY -> {
                    scoreTechnical = submission.getTechnicalScore() * weight;
                }
                case DELIVERY_TIME -> {
                    double delayRatio = 1.0 / submission.getDeliveryTimeDays();
                    scoreDelay = delayRatio * 100 * weight;
                }
            }
        }

        double finalScore = scorePrice + scoreTechnical + scoreDelay;

        return SubmissionEvaluationResult.builder()
                .scorePrice(scorePrice)
                .scoreTechnical(scoreTechnical)
                .scoreDelay(scoreDelay)
                .finalScore(finalScore)
                .build();
    }
}
