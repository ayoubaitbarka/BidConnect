package com.example.aiservice.repository;

import com.example.aiservice.entity.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, String> {

    Optional<DocumentMetadata> findByDocumentId(String documentId);
}
