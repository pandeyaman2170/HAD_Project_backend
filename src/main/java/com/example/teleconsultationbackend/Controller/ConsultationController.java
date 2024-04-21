package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.DateWiseConsultations;
import com.example.teleconsultationbackend.DTO.MonthWiseConsultation;
import com.example.teleconsultationbackend.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/consultation")
public class ConsultationController {
    @Autowired
    ConsultationService consultationService;

    @GetMapping("/totalDateWiseConsultations")
    public List<DateWiseConsultations> totalDateWiseConsultations() {
        return consultationService.totalDateWiseConsultations();
    }

    @GetMapping("/totalMonthWiseConsultations/{doctorId}")
    public List<MonthWiseConsultation> totalMonthWiseConsultations(@PathVariable Long doctorId) {
        return consultationService.totalMonthWiseConsultations(doctorId);
    }

    @GetMapping("/totalConsultationByDoctor/{doctorId}")
    public Long totalConsultationByDoctor(@PathVariable String doctorId) {
        return consultationService.totalConsultationByDoctor(Long.parseLong(doctorId));
    }

    @GetMapping("/totalConsultationByPatient/{patientId}")
    public Long totalConsultationByPatient(@PathVariable String patientId) {
        return consultationService.totalConsultationByPatient(Long.parseLong(patientId));
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/addConsultationStatus/{patientId}/{depId}")
    public void addConsultationStatusWaiting(@PathVariable String patientId, @PathVariable String depId){
        consultationService.addConsultationStatusWaitinghelper(Long.valueOf(patientId), Long.valueOf(depId));
    }

    @CrossOrigin
    @PostMapping("/accept_call/{doctorId}/{patientId}")
    public void setStatusToAccepted(@PathVariable String doctorId, @PathVariable String patientId){
        consultationService.setStatusToAcceptedHelper(Long.valueOf(doctorId), Long.valueOf(patientId));
        simpMessagingTemplate.convertAndSend("/topic/patient-waiting/"+patientId, Long.valueOf(doctorId));
    }
}
