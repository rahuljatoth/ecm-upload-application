package com.example.EcmUploadApplication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadRequest {
    private MultipartFile file;
    private String uploaderId;
    private String docType;
}
