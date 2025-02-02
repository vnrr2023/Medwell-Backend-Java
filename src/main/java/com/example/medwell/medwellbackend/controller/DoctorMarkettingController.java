package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.MarketToCustomersReqDto;
import com.example.medwell.medwellbackend.service.DoctorMarkettingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marketting")
public class DoctorMarkettingController {

    @Autowired
    private DoctorMarkettingService markettingService;

    @PostMapping("/market_services")
    public ResponseEntity<?> marketServicesToCustomer(@RequestBody MarketToCustomersReqDto marketToCustomersReqDto, HttpServletRequest request) throws Exception {
        Long user_id=Long.parseLong((String) request.getAttribute("user_id"));
        markettingService.sendEmailToCustomer(marketToCustomersReqDto,user_id);
        return ResponseEntity.status(200).build();
    }



}
