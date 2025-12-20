package com.example.tenderservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "document-service")
public interface DocumentClient {

    @PostMapping(
            value = "/api/documents/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    String upload(@RequestPart("file") MultipartFile file);
}
