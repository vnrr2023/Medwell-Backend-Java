package com.example.medwell.medwellbackend.dto.respdto;

import org.springframework.http.HttpStatus;

import java.util.Date;


public class ErrorResponse {
    private String mssg;
    private HttpStatus status;
    private Date timestamp;

    public ErrorResponse(String mssg, HttpStatus status, Date timestamp) {
        this.mssg = mssg;
        this.status = status;
        this.timestamp = timestamp;
    }
}
