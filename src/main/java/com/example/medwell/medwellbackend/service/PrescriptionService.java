package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.dto.reqdto.PrescriptionDto;
import com.example.medwell.medwellbackend.entity.Appointment;
import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorPrescription;
import com.example.medwell.medwellbackend.repository.AppointmentRepository;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorPrescriptionRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class PrescriptionService {



    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorPrescriptionRepository prescriptionRepository;

    public void createPrescription(Map<String, Object> prescriptionData, String otherData, String appointmentId) {

        Appointment appointment=appointmentRepository.getReferenceById(UUID.fromString(appointmentId));

        DoctorPrescription prescription=DoctorPrescription.builder()
                .prescription(prescriptionData)
                .otherInfo(otherData)
                .appointment(appointment)
                .build();
        prescriptionRepository.save(prescription);

    }


    public DoctorPrescription getDoctorPrescription(String appointmentId){
        Appointment appointment=appointmentRepository.getReferenceById(UUID.fromString(appointmentId));
        return prescriptionRepository.findByAppointment(appointment);
    }

    public DoctorPrescription updatePrescription(PrescriptionDto prescriptionDto){
        DoctorPrescription prescription=prescriptionRepository.getReferenceById(UUID.fromString(prescriptionDto.getId()));
        prescription.setOtherInfo(prescriptionDto.getOtherData());
        prescription.setPrescription(prescriptionDto.getPrescriptionData());
        return prescriptionRepository.save(prescription);
    }
}
