package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.PatientRegistrationService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    private PatientRegistrationService patientRegistrationService;


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
            patientRegistrationService.registerPatient(user);
        } else {
            throw new IllegalArgumentException("OTP Not verified");
        }
    }
}
