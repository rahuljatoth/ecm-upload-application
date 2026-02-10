package com.example.EcmUploadApplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =========================
       1. Validation Exceptions
       ========================= */

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(ValidationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error(e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodValidation(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error("Invalid request payload", HttpStatus.BAD_REQUEST));
    }

    /* =========================
       2. File Upload Exceptions
       ========================= */

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleMaxSize(MaxUploadSizeExceededException e) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(error("File size exceeds allowed limit", HttpStatus.PAYLOAD_TOO_LARGE));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Map<String, Object>> handleMultipart(MultipartException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error("Invalid multipart request", HttpStatus.BAD_REQUEST));
    }

    /* =========================
       3. File Type / Parsing
       ========================= */

    @ExceptionHandler(UnsupportedFileTypeException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedType(UnsupportedFileTypeException e) {
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(error(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }

    @ExceptionHandler(FileParsingException.class)
    public ResponseEntity<Map<String, Object>> handleParsing(FileParsingException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(error(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY));
    }

    /* =========================
       4. Storage / IO
       ========================= */

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, Object>> handleIO(IOException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error("File storage error", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleFileNotFound(FileNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error("File not found", HttpStatus.NOT_FOUND));
    }

    /* =========================
       5. Security / Access
       ========================= */

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error("Access denied", HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error("Authentication failed", HttpStatus.UNAUTHORIZED));
    }

    /* =========================
       6. Resource State Issues
       ========================= */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ResourceLockedException.class)
    public ResponseEntity<Map<String, Object>> handleLocked(ResourceLockedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error("File is currently locked", HttpStatus.CONFLICT));
    }

    /* =========================
       7. External ECM / Storage
       ========================= */

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<Map<String, Object>> handleExternal(ExternalServiceException e) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error("External ECM service unavailable", HttpStatus.SERVICE_UNAVAILABLE));
    }

    /* =========================
       8. Fallback (Safety Net)
       ========================= */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /* =========================
       Helper Method
       ========================= */

    private Map<String, Object> error(String message, HttpStatus status) {
        return Map.of(
                "status", "FAILED",
                "error", message,
                "code", status.value()
        );
    }
}
