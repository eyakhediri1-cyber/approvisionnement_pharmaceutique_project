/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.ResponseEntity
 *  org.springframework.validation.FieldError
 *  org.springframework.web.bind.MethodArgumentNotValidException
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.RestControllerAdvice
 */
package com.pharma.approvisionnement.exception;

import com.pharma.approvisionnement.exception.BusinessException;
import com.pharma.approvisionnement.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message, Object details) {
        LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("succes", false);
        body.put("message", message);
        if (details != null) {
            body.put("details", details);
        }
        return ResponseEntity.status((HttpStatusCode)status).body(body);
    }

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        LinkedHashMap errors = new LinkedHashMap();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError)error).getField();
            errors.put(field, error.getDefaultMessage());
        });
        return this.buildResponse(HttpStatus.BAD_REQUEST, "Erreur de validation", errors);
    }

    @ExceptionHandler(value={BusinessException.class})
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        return this.buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(value={ResourceNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return this.buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(value={IllegalArgumentException.class})
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return this.buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Erreur interne non g\u00e9r\u00e9e", (Throwable)ex);
        return this.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue. Veuillez r\u00e9essayer.", null);
    }
}

