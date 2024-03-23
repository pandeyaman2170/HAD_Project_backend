package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PrescriptionController {
    @Autowired
    PrescriptionService prescriptionService;

    @PostMapping("/addPrescription")
    public PrescriptionDetails addPrescription(@RequestBody PrescriptionDetails prescriptionDetails) {
        return prescriptionService.addPrescription(prescriptionDetails);
    }

    @GetMapping("/getPrescriptionsPatient/{patientId}")
    public List<PrescriptionDetails> getPrescriptionsPatient(@PathVariable Long patientId) {
        return prescriptionService.getPrescriptionsPatient(patientId);
    }

    @GetMapping("/getPrescriptionsDoctor/{patientId}")
    public List<PrescriptionDetails> getPrescriptionsDoctor(@PathVariable Long patientId) {
        return this.prescriptionService.getPrescriptionsDoctor(patientId);
    }
}
