package com.example.medwell.medwellbackend.dto.reqdto;

import lombok.Data;

@Data
public class AppointmentForPatientReqDto {
    private String service_id;
    private String slot_id;
    private String doctor_id;
}
