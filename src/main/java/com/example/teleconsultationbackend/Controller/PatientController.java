package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Service.PatientService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("patient")
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
        User user1 = registrationRequest.getUser();
        User user = new User();
        user.setAddress(user1.getAddress());
        user.setDob(user1.getDob());
        user.setCity(user1.getCity());
        user.setEmail(user1.getEmail());
        user.setGender(user1.getGender());
        user.setPhone(user.getPhone());
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setTitle(user1.getTitle());
        user.setPincode(user1.getPincode());
        user.setPhone(user1.getPhone());
        user.setRole("patient");
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
