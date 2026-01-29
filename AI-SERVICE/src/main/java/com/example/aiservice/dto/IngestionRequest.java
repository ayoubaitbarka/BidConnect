package com.example.aiservice.dto;

/**
 * Request to ingest a document into the RAG system
 */
public record IngestionRequest(
        String documentId, // ID from document-service
        String documentUrl // URL to download the document
) {
}
