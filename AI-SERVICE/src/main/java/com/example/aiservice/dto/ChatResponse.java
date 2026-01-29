package com.example.aiservice.dto;

import java.util.List;

/**
 * Response from RAG chat
 */
public record ChatResponse(
        String answer,
        List<String> sources, // Document IDs or chunks used
        String conversationId) {
}
