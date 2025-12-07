package com.example.tenderservice.entity;

import com.example.tenderservice.entity.enumeration.TenderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tenders")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Tender extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long organizationId;

    @Column(nullable = false)
    private String ownerUserId;

    @Enumerated(EnumType.STRING)
    private TenderStatus status = TenderStatus.DRAFT;

    private LocalDate publicationDate;

    private LocalDate deadline;

    @OneToMany(mappedBy = "tender", cascade = CascadeType.ALL)
    private List<EvaluationCriterion> criteria;

    @OneToMany(mappedBy = "tender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TenderDocumentRef> documents;
}