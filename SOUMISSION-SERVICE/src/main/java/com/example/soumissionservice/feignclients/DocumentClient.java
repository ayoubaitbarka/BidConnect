package com.example.soumissionservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "document-service", url = "http://localhost:8081")
@Component
public interface DocumentClient {

    @PostMapping(value = "/api/documents/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file);

    @DeleteMapping("/api/documents/{id}")
    void delete(@PathVariable("id") String documentId);
}
