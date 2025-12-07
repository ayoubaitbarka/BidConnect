package com.example.tenderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenderDocumentRefRequestDTO {
    private String documentId;
    private String fileName;
    private String contentType;
}
