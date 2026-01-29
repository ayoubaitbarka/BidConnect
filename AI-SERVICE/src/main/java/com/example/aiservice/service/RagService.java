package com.example.aiservice.service;

import com.example.aiservice.dto.ChatRequest;
import com.example.aiservice.dto.ChatResponse;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for RAG-based chat functionality
 * Retrieves relevant document chunks and generates contextual answers
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RagService {

    private final ChatLanguageModel chatModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final ChatMemory chatMemory;

    /**
     * Process a chat query using RAG
     */
    public ChatResponse chat(ChatRequest request) {
        log.info("Processing chat query: {}", request.query());

        try {
            log.info("Processing chat query: {}", request.query());

            // 1. Create content retriever for RAG
            ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                    .embeddingStore(embeddingStore)
                    .embeddingModel(embeddingModel)
                    .maxResults(5) // Retrieve top 5 relevant chunks
                    .minScore(0.5) // Lowered from 0.7 to increase recall
                    .build();

            log.debug("Content retriever configured with maxResults=5, minScore=0.5");

            // 2. Build AI Service with RAG
            Assistant assistant = AiServices.builder(Assistant.class)
                    .chatLanguageModel(chatModel)
                    .chatMemory(chatMemory)
                    .contentRetriever(retriever)
                    .build();

            // 3. Get answer
            String answer = assistant.chat(request.query());

            log.info("Chat response generated. Answer length: {} chars", answer.length());
            log.debug("Answer preview: {}", answer.substring(0, Math.min(100, answer.length())));

            // 4. Generate conversation ID if not provided
            String conversationId = request.conversationId() != null
                    ? request.conversationId()
                    : UUID.randomUUID().toString();

            log.info("Chat response generated successfully");

            return new ChatResponse(
                    answer,
                    List.of(), // TODO: Extract source document IDs from retrieved chunks
                    conversationId);

        } catch (Exception e) {
            log.error("Chat processing failed", e);
            return new ChatResponse(
                    "Sorry, I encountered an error processing your question: " + e.getMessage(),
                    List.of(),
                    request.conversationId());
        }
    }

    /**
     * AI Assistant interface for LangChain4j
     */
    interface Assistant {
        String chat(String userMessage);
    }
}
