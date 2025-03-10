package com.example.medwell.medwellbackend.dto.respdto;


import com.example.medwell.medwellbackend.entity.CustomUser;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientVisibleDoctorProfileDto {
    private UUID id;
    private String name,phoneNumber,profileQr,profilePic,speciality,education;
    private Long userId;
    private boolean verified;
}
