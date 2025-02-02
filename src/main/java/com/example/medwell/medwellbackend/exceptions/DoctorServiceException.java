package com.example.medwell.medwellbackend.exceptions;

public class DoctorServiceException extends RuntimeException{

    private String mssg;
    private int status;

    public DoctorServiceException(String mssg, int status) {
        this.mssg = mssg;
        this.status = status;
    }

    public String getMssg() {
        return mssg;
    }

    public void setMssg(String mssg) {
        this.mssg = mssg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
