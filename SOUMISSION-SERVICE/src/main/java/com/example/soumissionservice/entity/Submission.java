package com.example.soumissionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor

@Data
@Getter
@Setter
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String tenderId;
    private String supplierId;

//    private String DocUrl;
    private String documentId;



    @Enumerated(EnumType.STRING)
    private SubmissionStatus status= SubmissionStatus.SUBMITTED;

    private Double price;
    private Double technical;
    private Double deadline;
    private Double score;

    @Column(columnDefinition = "TEXT")
    private String ragAnalysis;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
