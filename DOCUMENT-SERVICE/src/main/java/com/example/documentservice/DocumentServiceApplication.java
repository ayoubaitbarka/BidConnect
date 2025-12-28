package com.example.documentservice;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DocumentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentServiceApplication.class, args);
    }

    // ✅ Test de connectivité avec MinIO
    @Bean
    public CommandLineRunner testMinio(MinioClient minioClient, @Value("${minio.bucket}") String bucket) {
        return args -> {
            boolean exists = minioClient.bucketExists(
                    io.minio.BucketExistsArgs.builder().bucket(bucket).build()
            );
            System.out.println("Le bucket '" + bucket + "' existe ? " + exists);
        };
    }
}
