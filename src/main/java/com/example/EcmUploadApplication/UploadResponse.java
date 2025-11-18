package com.example.EcmUploadApplication;

import java.time.Instant;

@Data
@Builder
public class UploadResponse {
    private String documentId;
    private String status;
    private Instant processedAt;
    private long latencyMs;
}

