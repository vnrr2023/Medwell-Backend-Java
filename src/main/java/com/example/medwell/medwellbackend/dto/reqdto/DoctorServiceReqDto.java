package com.example.medwell.medwellbackend.dto.reqdto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DoctorServiceReqDto {
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("amount")
    private String amount;
}
