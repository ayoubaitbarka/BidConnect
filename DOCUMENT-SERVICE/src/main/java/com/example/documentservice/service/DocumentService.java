package com.example.documentservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    String upload(MultipartFile file);
}