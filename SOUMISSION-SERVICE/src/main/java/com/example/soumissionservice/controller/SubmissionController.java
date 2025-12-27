package com.example.soumissionservice.controller;

import com.example.soumissionservice.dto.StatusUpdateRequest;
import com.example.soumissionservice.dto.SubmissionRequest;
import com.example.soumissionservice.dto.SubmissionResponse;
import com.example.soumissionservice.services.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public SubmissionResponse submit(@ModelAttribute SubmissionRequest req) {
        return submissionService.createSubmission(req);
    }

    @DeleteMapping("/{id}")
    public void deleteSubmission(@PathVariable String id) {
        submissionService.deleteSubmission(id);
    }


    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable String id,
                             @RequestBody StatusUpdateRequest req) {
        submissionService.updateStatus(id, req.status());
    }
    @GetMapping("/{id}")
    public SubmissionResponse getSubmission(@PathVariable String id) {
        return submissionService.findSubmission(id);
    }

    @GetMapping("/tender/{tenderId}")
    public List<SubmissionResponse> getByTender(@PathVariable String tenderId) {
        return submissionService.getByTender(tenderId);
    }

}

