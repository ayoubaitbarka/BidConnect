package com.example.aiservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity to track ingested documents metadata in PostgreSQL
 */
@Entity
@Table(name = "document_metadata")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String documentId; // Reference to document-service

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private LocalDateTime ingestedAt;

    @Column
    private Integer chunkCount; // Number of embeddings created

    @Column
    private String status; // PENDING, COMPLETED, FAILED
}
