package com.example.documentservice.service;

import com.example.documentservice.entity.Document;
import com.example.documentservice.repository.DocumentRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final MinioClient minioClient;
    private final DocumentRepository repo;

    @Value("${minio.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile file) {

        try {
            String documentId = UUID.randomUUID().toString();
            String objectName = documentId + "-" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String url = "/api/documents/" + documentId + "/download";

            Document doc = Document.builder()
                    .id(documentId)
                    .fileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .objectName(objectName)
                    .url(url)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            repo.save(doc);

//            return url;
            return documentId;

        } catch (Exception e) {
            throw new RuntimeException("Upload failed", e);
        }
    }
}
