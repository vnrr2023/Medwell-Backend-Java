package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.dto.reqdto.MarketToCustomersReqDto;
import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorServiceMarketting;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceMarkettingRepository;
import com.example.medwell.medwellbackend.utility.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorMarkettingService {

    @Autowired
    private MailUtility mailUtility;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private DoctorServiceMarkettingRepository serviceMarkettingRepository;

    public void sendEmailToCustomer(MarketToCustomersReqDto marketToCustomersReqDto, Long user_id) throws Exception {
        CustomUser doctor=customUserRepository.getReferenceById(user_id);
        DoctorServiceMarketting serviceMarketting=DoctorServiceMarketting.builder().html(marketToCustomersReqDto.getHtml()).doctor(doctor).build();
        serviceMarkettingRepository.save(serviceMarketting);
        for(String customerEmail:marketToCustomersReqDto.getEmails()){
            mailUtility.sendMarkettingEmails(customerEmail,marketToCustomersReqDto.getHtml(),marketToCustomersReqDto.getSubject());
        }

    }
}
