package com.example.medwell.medwellbackend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DoctorServiceException extends RuntimeException{

    private String mssg;
    private HttpStatus status;

    public DoctorServiceException(String mssg, HttpStatus status) {
        this.mssg = mssg;
        this.status = status;
    }


}
