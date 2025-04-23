package com.example.medwell.medwellbackend.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorServiceException.class)
    public ResponseEntity<?> handleGlobalException(DoctorServiceException e) {
        ErrorResponseDTO errorResponse=new ErrorResponseDTO(e.getMssg(),e.getStatus().value());
        log.error("{}", e.getMessage());
        return ResponseEntity.status(e.getStatus().value()).body(errorResponse);
    }



    // You can add more exception handlers here (e.g., for BadRequest, Unauthorized, etc.)
}
