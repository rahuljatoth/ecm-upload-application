package com.example.EcmUploadApplication;

@Data
@Builder
public class UploadRequest {
    private MultipartFile file;
    private String uploaderId;
    private String docType;
}
