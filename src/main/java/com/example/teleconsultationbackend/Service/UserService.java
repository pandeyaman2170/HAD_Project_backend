package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.PatientLoginStatus;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;
    public PatientLoginStatus login(String phone, String role) {
        User user = userRepository.findByPhoneAndRole(phone, role);
        PatientLoginStatus patientLoginStatus = new PatientLoginStatus();
        if (user != null) {
            patientLoginStatus.setId(patientRepository.findPatientByUserId(user.getId()).getId());
            patientLoginStatus.setIsValid(true);
        } else {
            patientLoginStatus.setIsValid(false);
            patientLoginStatus.setId(null);
        }
        return patientLoginStatus;
    }
}