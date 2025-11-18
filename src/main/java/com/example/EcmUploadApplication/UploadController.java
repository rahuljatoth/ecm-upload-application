package com.example.EcmUploadApplication;


import com.example.ecmupload.dto.UploadRequest;
import com.example.ecmupload.dto.UploadResponse;
import com.example.ecmupload.service.UploadOrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class UploadController {

    protected final UploadOrchestratorService orchestratorService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploaderId") String uploaderId,
            @RequestParam("docType") String docType) {

        UploadRequest request = UploadRequest.builder()
                .file(file)
                .uploaderId(uploaderId)
                .docType(docType)
                .build();

        UploadResponse response = orchestratorService.handleUpload(request);
        return ResponseEntity.ok(response);
    }
}
