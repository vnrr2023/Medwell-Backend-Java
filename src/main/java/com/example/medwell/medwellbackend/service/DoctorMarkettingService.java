package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.dto.reqdto.MarketToCustomersReqDto;
import com.example.medwell.medwellbackend.entity.CustomUser;
import com.example.medwell.medwellbackend.entity.DoctorServiceMarketting;
import com.example.medwell.medwellbackend.repository.CustomUserRepository;
import com.example.medwell.medwellbackend.repository.DoctorServiceMarkettingRepository;
import com.example.medwell.medwellbackend.utility.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        DoctorServiceMarketting serviceMarketting=DoctorServiceMarketting.builder().html(marketToCustomersReqDto.getHtml()).doctor(doctor).customerCount(marketToCustomersReqDto.getEmails().size()).build();
        serviceMarkettingRepository.save(serviceMarketting);
        for(String customerEmail:marketToCustomersReqDto.getEmails()){
            mailUtility.sendMarkettingEmails(customerEmail,marketToCustomersReqDto.getHtml(),marketToCustomersReqDto.getSubject());
        }

    }

    public ResponseEntity<?> getMarkettedSerices(Long user_id) {
        List<DoctorServiceMarketting> markettingList=serviceMarkettingRepository.findByDoctor(customUserRepository.getReferenceById(user_id));
        return ResponseEntity.status(200).body(Map.of("count",markettingList.size(),"list",markettingList));

    }

    public ResponseEntity<?> getMarkettedServicesCount(Long user_id){
        int count=serviceMarkettingRepository.countByDoctor(customUserRepository.getReferenceById(user_id));
        return ResponseEntity.status(200).body(Map.of("count",count));
    }
}
