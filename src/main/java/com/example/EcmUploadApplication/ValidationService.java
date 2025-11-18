package com.example.EcmUploadApplication;

import org.hibernate.boot.Metadata;
import org.springframework.stereotype.Component;

@Component
public class ValidationService {

    public void validate(Metadata metadata, String docType) {
        if (metadata.getCustomerName() == null)
            throw new ValidationException("Customer name missing");

        if (metadata.getAccountNo() == null)
            throw new ValidationException("Account number missing");

        // Business rules per doc type
        if (docType.equals("ID_PROOF") && metadata.getDob() == null)
            throw new ValidationException("DOB required for ID docs");
    }
}
