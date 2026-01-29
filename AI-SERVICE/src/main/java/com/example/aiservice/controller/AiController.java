package com.example.aiservice.controller;

import com.example.aiservice.dto.*;
import com.example.aiservice.service.IngestionService;
import com.example.aiservice.service.RagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for AI/RAG operations
 * Provides endpoints for document ingestion and RAG-based chat
 */
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Tag(name = "AI Service", description = "RAG-based document analysis and chat")
public class AiController {

    private final IngestionService ingestionService;
    private final RagService ragService;

    /**
     * Ingest a document into the RAG system
     */
    @Operation(summary = "Ingest Document", description = "Load a document from URL, split into chunks, generate embeddings, and store in vector database")
    @PostMapping("/ingest")
    public ResponseEntity<IngestionResponse> ingestDocument(@RequestBody IngestionRequest request) {
        IngestionResponse response = ingestionService.ingestDocument(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Chat with RAG system
     */
    @Operation(summary = "RAG Chat", description = "Ask questions about ingested documents. The system retrieves relevant context and generates answers.")
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        ChatResponse response = ragService.chat(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AI Service is running");
    }
}
