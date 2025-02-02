package com.example.medwell.medwellbackend.service;

import com.example.medwell.medwellbackend.dto.reqdto.MarketToCustomersReqDto;
import com.example.medwell.medwellbackend.utility.MailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorMarkettingService {

    @Autowired
    private MailUtility mailUtility;

    public void sendEmailToCustomer(MarketToCustomersReqDto marketToCustomersReqDto) throws Exception {
        for(String customerEmail:marketToCustomersReqDto.getEmails()){
            mailUtility.sendMarkettingEmails(customerEmail,marketToCustomersReqDto.getHtml(),marketToCustomersReqDto.getSubject());
        }

    }
}
