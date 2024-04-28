package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.ConsultationDetails;
import com.example.teleconsultationbackend.DTO.DateWiseConsultations;
import com.example.teleconsultationbackend.DTO.MonthWiseConsultation;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Service.ConsultationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/consultation")
@Tag(
        name="Consultation APIs"
)
public class ConsultationController {
    @Autowired
    ConsultationService consultationService;

    @Autowired
    private DepartmentRepository departmentRepository;

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

    @GetMapping("/getAllConsultations/{depName}/{hospitalId}")
    public List<ConsultationDetails> totalConsultationByDep(@PathVariable String depName, @PathVariable Long hospitalId) {
        Long depId = departmentRepository.findDepartmentByName(depName).getId();
        System.out.println("Inside the API");
        return consultationService.totalConsultationByDep(depId, hospitalId);
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
    public void setStatusToAccepted(@PathVariable Long doctorId, @PathVariable String patientId){
        consultationService.setStatusToAcceptedHelper(doctorId, Long.valueOf(patientId));
        simpMessagingTemplate.convertAndSend("/topic/patient-waiting/"+patientId, doctorId);
    }

    @CrossOrigin
    @PostMapping("/getRepeatStatus/{patientId}/{doctorId}")
    public String getRepeatStatus(@PathVariable Long patientId, @PathVariable Long doctorId){
        return consultationService.getRepeatStatusHelper(patientId, doctorId);
    }
}
