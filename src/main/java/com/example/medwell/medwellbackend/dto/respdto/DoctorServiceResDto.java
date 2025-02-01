package com.example.medwell.medwellbackend.dto.respdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorServiceResDto {
    private String serviceName;
    private String message;
}
