package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
    @Autowired
    PrescriptionService prescriptionService;

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/addPrescription")
    public PrescriptionDetails addPrescription(@RequestBody PrescriptionDetails prescriptionDetails) {
        return prescriptionService.addPrescription(prescriptionDetails);
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
