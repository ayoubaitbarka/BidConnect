package com.example.documentservice.service;

import com.example.documentservice.entity.Document;
import com.example.documentservice.repository.DocumentRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
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
    @Override
    public ResponseEntity<byte[]> download(String documentId) {

        try {
            // 1️⃣ Chercher le document en DB
            Document document = repo.findById(documentId)
                    .orElseThrow(() ->
                            new RuntimeException("Document not found"));

            // 2️⃣ Lire le fichier depuis MinIO
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(document.getObjectName())
                            .build()
            );

            byte[] fileBytes = stream.readAllBytes();

            // 3️⃣ Construire la réponse HTTP
            return ResponseEntity.ok()
                    .contentType(
                            MediaType.parseMediaType(document.getContentType())
                    )
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + document.getFileName() + "\""
                    )
                    .body(fileBytes);

        } catch (Exception e) {
            throw new RuntimeException("Download failed", e);
        }
    }
    @Override
    public void delete(String documentId) {

        try {
            // 1️⃣ Récupérer le document depuis la DB
            Document document = repo.findById(documentId)
                    .orElseThrow(() ->
                            new RuntimeException("Document not found"));

            // 2️⃣ Supprimer le fichier dans MinIO
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(document.getObjectName())
                            .build()
            );

            // 3️⃣ Supprimer l’entrée en base
            repo.deleteById(documentId);

        } catch (Exception e) {
            throw new RuntimeException("Delete failed", e);
        }
    }


}
