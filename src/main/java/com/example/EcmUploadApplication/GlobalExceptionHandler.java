package com.example.EcmUploadApplication;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(ValidationException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage(), "status", "FAILED"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        return ResponseEntity.internalServerError().body(Map.of("error", "Internal error", "status", "FAILED"));
    }
}
