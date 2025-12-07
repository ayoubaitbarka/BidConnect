package com.example.tenderservice.entity;

import com.example.tenderservice.entity.enumeration.EvaluationCriterionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evaluation_criteria")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EvaluationCriterion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationCriterionType type;

    @Column(nullable = false)
    private Integer weight; // ex : 40% prix, 40% technique, 20% délai

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tender_id")
    private Tender tender;
}