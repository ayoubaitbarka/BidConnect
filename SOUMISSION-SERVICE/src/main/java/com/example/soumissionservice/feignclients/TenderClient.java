package com.example.soumissionservice.feignclients;

import com.example.soumissionservice.dto.TenderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tender-service", url = "http://localhost:8080")
@Component
public interface TenderClient {

    @GetMapping("/api/v1/tenders/{id}")
    TenderResponse getTender(@PathVariable("id") String id);

    // @GetMapping("/api/tenders/")
    // void getCriteria(@RequestBody Criteria criteria);

}
