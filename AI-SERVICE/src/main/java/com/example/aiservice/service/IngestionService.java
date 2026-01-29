package com.example.aiservice.service;

import com.example.aiservice.dto.IngestionRequest;
import com.example.aiservice.dto.IngestionResponse;
import com.example.aiservice.entity.DocumentMetadata;
import com.example.aiservice.repository.DocumentMetadataRepository;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for ingesting documents into the RAG system
 * Downloads documents, splits them into chunks, generates embeddings, and
 * stores in Qdrant
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IngestionService {

        private final DocumentMetadataRepository metadataRepository;
        private final EmbeddingStore<TextSegment> embeddingStore;
        private final EmbeddingModel embeddingModel;

        /**
         * Ingest a document from the document-service
         */
        @Transactional
        public IngestionResponse ingestDocument(IngestionRequest request) {
                log.info("Starting ingestion for document: {}", request.documentId());

                try {
                        // 1. Check if already ingested
                        if (metadataRepository.findByDocumentId(request.documentId()).isPresent()) {
                                return new IngestionResponse(
                                                request.documentId(),
                                                "ALREADY_EXISTS",
                                                null,
                                                "Document already ingested");
                        }

                        // 2. Download document from URL
                        Document document = UrlDocumentLoader.load(
                                        request.documentUrl(),
                                        new ApacheTikaDocumentParser());
                        log.info("Document loaded: {} chars", document.text().length());

                        // 3. Configure splitter
                        DocumentSplitter splitter = DocumentSplitters.recursive(
                                        500, // chunk size
                                        50 // overlap
                        );

                        // 4. Create embeddings and store in Qdrant
                        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                                        .embeddingStore(embeddingStore)
                                        .embeddingModel(embeddingModel)
                                        .documentSplitter(splitter)
                                        .build();

                        ingestor.ingest(document);
                        log.info("Embeddings stored in Qdrant");

                        // 5. Save metadata to PostgreSQL
                        // Note: We don't have direct access to segment count, estimate from document
                        // size
                        int estimatedChunks = document.text().length() / 500;
                        DocumentMetadata metadata = DocumentMetadata.builder()
                                        .documentId(request.documentId())
                                        .fileName(extractFileName(request.documentUrl()))
                                        .contentType("application/pdf") // TODO: detect from URL
                                        .ingestedAt(LocalDateTime.now())
                                        .chunkCount(estimatedChunks)
                                        .status("COMPLETED")
                                        .build();

                        metadataRepository.save(metadata);

                        return new IngestionResponse(
                                        request.documentId(),
                                        "COMPLETED",
                                        estimatedChunks,
                                        "Document successfully ingested");

                } catch (Exception e) {
                        log.error("Ingestion failed for document: {}", request.documentId(), e);

                        // Save failed status
                        DocumentMetadata metadata = DocumentMetadata.builder()
                                        .documentId(request.documentId())
                                        .fileName(extractFileName(request.documentUrl()))
                                        .contentType("unknown")
                                        .ingestedAt(LocalDateTime.now())
                                        .chunkCount(0)
                                        .status("FAILED")
                                        .build();
                        metadataRepository.save(metadata);

                        return new IngestionResponse(
                                        request.documentId(),
                                        "FAILED",
                                        0,
                                        "Ingestion failed: " + e.getMessage());
                }
        }

        private String extractFileName(String url) {
                String[] parts = url.split("/");
                return parts[parts.length - 1];
        }
}
