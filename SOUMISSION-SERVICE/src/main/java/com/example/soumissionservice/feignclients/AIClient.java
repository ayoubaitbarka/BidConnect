package com.example.soumissionservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ai-service")
@Component
public interface AIClient {


    @PostMapping("/api/rag/analyze/{submissionId}")
    String analyze(@PathVariable String submissionId);
}

