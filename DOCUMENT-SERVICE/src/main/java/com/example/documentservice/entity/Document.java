package com.example.documentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    private String id;

    private String fileName;
    private String contentType;
//  nom réel dans MinIO :
    private String objectName;
//  url retournée aux autres services :
    private String url;
    private LocalDateTime uploadedAt;
}
