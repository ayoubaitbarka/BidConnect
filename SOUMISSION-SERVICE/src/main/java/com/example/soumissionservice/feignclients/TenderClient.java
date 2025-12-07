package com.example.soumissionservice.feignclients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tender-service")
@Component
public interface TenderClient {

    @GetMapping("/api/tenders/{id}/validate-open")
    void validateTenderOpen(@PathVariable String id);

    @GetMapping("/api/tenders/")
    void getCriteria(@RequestBody Criteria criteria);

}

