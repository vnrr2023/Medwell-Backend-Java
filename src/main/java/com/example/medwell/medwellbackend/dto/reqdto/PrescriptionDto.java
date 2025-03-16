package com.example.medwell.medwellbackend.dto.reqdto;


import lombok.Data;

import java.util.Map;

@Data
public class PrescriptionDto {

    private String id;
    private Map<String,Object> prescriptionData;
    private String otherData;
    private String appointmentId;

}
