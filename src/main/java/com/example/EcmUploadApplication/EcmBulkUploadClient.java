package com.example.EcmUploadApplication;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.boot.Metadata;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class EcmBulkUploadClient {

    private final RestTemplate restTemplate;

    @Value("${ecm.bulkUpload.url}")
    private String bulkUploadUrl;

    public EcmBulkUploadClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
