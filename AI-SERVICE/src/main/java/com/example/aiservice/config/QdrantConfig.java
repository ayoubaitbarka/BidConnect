package com.example.aiservice.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;

/**
 * Qdrant-specific configuration
 * Handles collection creation and initialization
 */
@Configuration
@Slf4j
public class QdrantConfig {

    @Value("${langchain4j.qdrant.host}")
    private String qdrantHost;

    @Value("${langchain4j.qdrant.port}")
    private int qdrantPort;

    @Value("${langchain4j.qdrant.collection-name}")
    private String collectionName;

    @Bean
    public QdrantClient qdrantClient() {
        log.info("Initializing Qdrant client: {}:{}", qdrantHost, qdrantPort);
        QdrantClient client = new QdrantClient(
                QdrantGrpcClient.newBuilder(qdrantHost, qdrantPort, false).build());

        // Ensure collection exists
        ensureCollectionExists(client);

        return client;
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(QdrantClient qdrantClient) {
        return QdrantEmbeddingStore.builder()
                .client(qdrantClient)
                .collectionName(collectionName)
                .payloadTextKey("text")
                .build();
    }

    private void ensureCollectionExists(QdrantClient client) {
        try {
            // Check if collection exists
            Collections.CollectionInfo collectionInfo = client.getCollectionInfoAsync(collectionName).get();
            log.info("Collection '{}' exists with {} vectors", collectionName, collectionInfo.getVectorsCount());
        } catch (ExecutionException e) {
            if (e.getCause() instanceof io.grpc.StatusRuntimeException) {
                io.grpc.StatusRuntimeException grpcException = (io.grpc.StatusRuntimeException) e.getCause();
                if (grpcException.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                    log.info("Collection '{}' does not exist, creating it now...", collectionName);
                    createCollection(client);
                } else {
                    log.error("gRPC error checking collection: {}", grpcException.getMessage());
                }
            } else {
                log.error("Error checking collection existence", e);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while checking collection", e);
        }
    }

    private void createCollection(QdrantClient client) {
        try {
            // Create collection with vector configuration
            // OpenAI text-embedding-3-small uses 1536 dimensions
            Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder()
                    .setSize(1536) // Dimension for OpenAI text-embedding-3-small
                    .setDistance(Collections.Distance.Cosine)
                    .build();

            client.createCollectionAsync(collectionName, vectorParams).get();

            log.info("Successfully created collection '{}'", collectionName);
        } catch (Exception e) {
            log.error("Failed to create collection '{}'", collectionName, e);
            throw new RuntimeException("Failed to create Qdrant collection", e);
        }
    }
}
