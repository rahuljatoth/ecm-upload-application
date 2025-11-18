package com.example.EcmUploadApplication;

import org.hibernate.boot.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class EcmBulkUploadClient {

    private final RestTemplate restTemplate;

    @Value("${ecm.bulkUpload.url}")
    private String bulkUploadUrl;

    public String uploadDocument(MultipartFile file, Metadata metadata) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
        body.add("metadata", new ObjectMapper().writeValueAsString(metadata));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<EcmUploadResponse> response =
                restTemplate.postForEntity(bulkUploadUrl, requestEntity, EcmUploadResponse.class);

        return response.getBody().getDocumentId();
    }
}
