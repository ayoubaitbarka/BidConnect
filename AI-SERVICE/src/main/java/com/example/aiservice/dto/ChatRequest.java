package com.example.aiservice.dto;

/**
 * Request for RAG chat
 */
public record ChatRequest(
        String query,
        String conversationId // Optional: for maintaining chat history
) {
}
