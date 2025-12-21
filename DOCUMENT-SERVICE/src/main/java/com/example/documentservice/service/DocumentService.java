package com.example.documentservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    String upload(MultipartFile file);
    ResponseEntity<byte[]> download(String documentId);
    void delete(String documentId);
}