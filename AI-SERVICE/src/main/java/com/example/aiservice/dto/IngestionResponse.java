package com.example.aiservice.dto;

/**
 * Response after ingesting a document
 */
public record IngestionResponse(
        String documentId,
        String status,
        Integer chunkCount,
        String message) {
}
