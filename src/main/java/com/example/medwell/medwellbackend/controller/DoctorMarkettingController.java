package com.example.medwell.medwellbackend.controller;


import com.example.medwell.medwellbackend.dto.reqdto.GenerateMarkettingMailBodyReqDto;
import com.example.medwell.medwellbackend.dto.reqdto.MarketToCustomersReqDto;
import com.example.medwell.medwellbackend.service.DoctorMarkettingService;
import com.example.medwell.medwellbackend.service.GroqUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> marketServicesToCustomer(@RequestBody MarketToCustomersReqDto marketToCustomersReqDto, @RequestAttribute("user_id") Long user_id) throws Exception {
        markettingService.sendEmailToCustomer(marketToCustomersReqDto,user_id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/get-marketted-services")
    public ResponseEntity<?> getMarkettedServices(@RequestAttribute("user_id") Long user_id){
        return markettingService.getMarkettedSerices(user_id);

    }

    @GetMapping("/get-number-of-marketting")
    public ResponseEntity<?> getNumberOfMarketting(@RequestAttribute("user_id") Long user_id){
        return markettingService.getMarkettedServicesCount(user_id);

    }

//    @PostMapping("/generate_mail_body")
//    public ResponseEntity<?> generateMailBody(@RequestBody GenerateMarkettingMailBodyReqDto reqDto) throws Exception {
//        return groqUtility.chatWithGroq(reqDto.getSubject());
//
//    }

}
