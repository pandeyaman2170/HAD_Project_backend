package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Service.PatientService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    // Wrapper class for both User and otpFlag
    @Getter
    public static class RegistrationRequest {
        private User user;
        private boolean otpFlag;

    }
    @PostMapping("/register-patient")
    public void registerPatient(@RequestBody RegistrationRequest registrationRequest) {
        User user = registrationRequest.getUser();
        boolean otpFlag = registrationRequest.isOtpFlag();
        if (otpFlag) {
            patientService.registerPatient(user);
        } else {
            throw new IllegalArgumentException("OTP Not verified");
        }
    }

    @PostMapping("/joinQueue/{dep_id}/{pid}")
    public void joinQueue(@PathVariable Long pid, @PathVariable Long dep_id){
        Patient patient = patientRepository.findPatientById(pid);
        System.out.println("patientName : " + patient.getUser().getFirstName());
        patientService.joinQueue(patient, dep_id);
    }

    @PostMapping("/delete-from-queue/{pid}")
    public void deletePatientFromQueue(@PathVariable Long pid){
        patientService.deletePatientFromQueue(pid);
    }
}
