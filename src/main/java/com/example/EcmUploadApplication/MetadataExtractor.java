package com.example.EcmUploadApplication;

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
