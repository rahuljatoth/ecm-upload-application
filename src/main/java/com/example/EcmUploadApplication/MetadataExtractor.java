package com.example.EcmUploadApplication;

import org.hibernate.boot.Metadata;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Component
public class MetadataExtractor {

    public Metadata extract(MultipartFile file) {
        // Placeholder for OCR / NLP / Regex logic
        // In real systems this may call Tesseract, Textract, OpenNLP, etc.
        Metadata metadata = new Metadata();
        metadata.setCustomerName("John Doe");   // extracted
        metadata.setAccountNo("ACC12345");      // extracted
        metadata.setDob("1990-01-01");          // extracted
        metadata.setUploadDate(LocalDate.now());
        return metadata;
    }
}
