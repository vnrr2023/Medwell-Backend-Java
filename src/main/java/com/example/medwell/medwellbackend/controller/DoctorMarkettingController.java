package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.GenerateMarkettingMailBodyReqDto;
import com.example.medwell.medwellbackend.dto.reqdto.MarketToCustomersReqDto;
import com.example.medwell.medwellbackend.exceptions.DoctorServiceException;
import com.example.medwell.medwellbackend.service.DoctorMarkettingService;
import com.example.medwell.medwellbackend.service.GroqUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketting")
public class DoctorMarkettingController {

    @Autowired
    private DoctorMarkettingService markettingService;

    @Autowired
    private GroqUtility groqUtility;

    @PostMapping("/market-services")
    public ResponseEntity<?> marketServicesToCustomer(@RequestBody MarketToCustomersReqDto marketToCustomersReqDto, @RequestAttribute("user_id") Long user_id) {
        try {
            markettingService.sendEmailToCustomer(marketToCustomersReqDto,user_id);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-marketted-services")
    public ResponseEntity<?> getMarkettedServices(@RequestAttribute("user_id") Long user_id){
        try {
            return markettingService.getMarkettedSerices(user_id);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-number-of-marketting")
    public ResponseEntity<?> getNumberOfMarketting(@RequestAttribute("user_id") Long user_id){
        try {
            return markettingService.getMarkettedServicesCount(user_id);
        } catch (Exception e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }



    @PostMapping("/whatsapp")
    public ResponseEntity<?> marketUsingWhatsApp(@RequestAttribute("user_id") long userId,
                                                 @RequestParam("message") String message
    ) throws JsonProcessingException {
        try {
            markettingService.sendWhatsAppMessages(userId,message);
            return ResponseEntity.status(200).build();
        } catch (JsonProcessingException e) {
            throw new DoctorServiceException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
