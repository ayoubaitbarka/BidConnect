package com.example.soumissionservice.controller;

import com.example.soumissionservice.dto.StatusUpdateRequest;
import com.example.soumissionservice.dto.SubmissionRequest;
import com.example.soumissionservice.dto.SubmissionResponse;
import com.example.soumissionservice.services.SubmissionService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Submission Management", description = "APIs for managing submissions")

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @Operation(summary = "Create a new submission", description = "Create a new submission for a tender")
    @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public SubmissionResponse submit(@ModelAttribute SubmissionRequest req) {
        return submissionService.createSubmission(req);
    }

    @Operation(summary = "Delete a submission", description = "Delete a submission by its ID")
    @DeleteMapping("/{id}")
    public void deleteSubmission(@PathVariable String id) {
        submissionService.deleteSubmission(id);
    }

    @Operation(summary = "Update submission status", description = "Update the status of a submission")
    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable String id,
            @RequestBody StatusUpdateRequest req) {
        submissionService.updateStatus(id, req.status());
    }

    @Operation(summary = "Get submission by ID", description = "Retrieve a submission by its ID")
    @GetMapping("/{id}")
    public SubmissionResponse getSubmission(@PathVariable String id) {
        return submissionService.findSubmission(id);
    }

    @Operation(summary = "Get submissions by tender ID", description = "Retrieve all submissions for a specific tender")
    @GetMapping("/tender/{tenderId}")
    public List<SubmissionResponse> getByTender(@PathVariable String tenderId) {
        return submissionService.getByTender(tenderId);
    }

}
