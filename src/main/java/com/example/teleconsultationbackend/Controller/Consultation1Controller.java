package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Repository.Consultation1Repository;
import com.example.teleconsultationbackend.Service.Consultation1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("consultation1")
public class Consultation1Controller {
    @Autowired
    private Consultation1Service consultation1Service;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/addConsultationStatus/{patientId}/{depId}")
    public void addConsultationStatusWaiting(@PathVariable String patientId, @PathVariable String depId){
        consultation1Service.addConsultationStatusWaitinghelper(Long.valueOf(patientId), Long.valueOf(depId));
    }

    @CrossOrigin
    @PostMapping("/accept_call/{doctorId}/{patientId}")
    public void setStatusToAccepted(@PathVariable String doctorId, @PathVariable String patientId){
        consultation1Service.setStatusToAcceptedHelper(Long.valueOf(doctorId), Long.valueOf(patientId));
        simpMessagingTemplate.convertAndSend("/topic/patient-waiting/"+patientId, Long.valueOf(doctorId));
    }

}
