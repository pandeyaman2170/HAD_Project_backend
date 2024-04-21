package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Service.PrescriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/prescription")
@Tag(
        name="Prescription APIs"
)
public class PrescriptionController {
    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/addPrescription")
    public PrescriptionDetails addPrescription(@RequestBody PrescriptionDetails prescriptionDetails) {
        PrescriptionDetails prescriptionDetails1 = prescriptionService.addPrescription(prescriptionDetails);
        simpMessagingTemplate.convertAndSend("/topic/patient-end-call/" + prescriptionDetails.getPatientId(), prescriptionDetails.getDoctorId());
        return prescriptionDetails1;
    }



    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getPrescriptionsPatient/{patientId}")
    public List<PrescriptionDetails> getPrescriptionsPatient(@PathVariable Long patientId) {
        return prescriptionService.getPrescriptionsPatient(patientId);
    }



    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getPrescriptionsDoctor/{patientId}")
    public List<PrescriptionDetails> getPrescriptionsDoctor(@PathVariable Long patientId) {
        return this.prescriptionService.getPrescriptionsDoctor(patientId);
    }
}
