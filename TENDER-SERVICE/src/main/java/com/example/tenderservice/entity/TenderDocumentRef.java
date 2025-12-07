package com.example.tenderservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tender_documents")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TenderDocumentRef {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentId;

    private String url;

    private String fileName;

    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tender_id")
    private Tender tender;
}
