package com.example.EcmUploadApplication;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UploadResponse {
    private String documentId;
    private String status;
    private Instant processedAt;
    private long latencyMs;
}

