package com.example.soumissionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String tenderId;
    private String supplierId;

    private String DocUrl;


    @Enumerated(EnumType.STRING)
    private SubmissionStatus status= SubmissionStatus.SUBMITTED;

    private Double Price;
    private Double Technical;
    private Double Deadline;
    private Double score;

    @Column(columnDefinition = "TEXT")
    private String ragAnalysis;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
