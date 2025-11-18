package com.example.EcmUploadApplication;

import org.hibernate.boot.Metadata;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UploadOrchestratorService {

    private final MetadataExtractor metadataExtractor;
    private final ValidationService validationService;
    private final EcmBulkUploadClient ecmBulkUploadClient;

    public UploadResponse handleUpload(UploadRequest request) {
        long start = System.currentTimeMillis();

        // 1. Extract metadata
        Metadata metadata = metadataExtractor.extract(request.getFile());

        // 2. Business validation
        validationService.validate(metadata, request.getDocType());

        // 3. Combine metadata with user input
        metadata.setUploaderId(request.getUploaderId());
        metadata.setDocumentType(request.getDocType());

        // 4. Call ECM Upload API
        String documentId = ecmBulkUploadClient.uploadDocument(
                request.getFile(),
                metadata
        );

        return UploadResponse.builder()
                .documentId(documentId)
                .status("SUCCESS")
                .processedAt(Instant.now())
                .latencyMs(System.currentTimeMillis() - start)
                .build();
    }
}
