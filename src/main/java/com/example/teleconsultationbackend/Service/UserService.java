package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.UserLoginStatus;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;
    public UserLoginStatus login(String phone, String role) {
        User user = userRepository.findByPhoneAndRole(phone, role);
        UserLoginStatus userLoginStatus = new UserLoginStatus();
        if(user != null) {
            if(role.equals("patient")) {
                userLoginStatus.setId(patientRepository.findPatientByUserId(user.getId()).getId());
                userLoginStatus.setIsValid(true);
            }else {
                userLoginStatus.setId(doctorRepository.findDoctorByUserId(user.getId()).getId());
                userLoginStatus.setIsValid(true);
            }
        }else {
            userLoginStatus.setIsValid(false);
            userLoginStatus.setId(null);
        }
        return userLoginStatus;
    }

    public Long getTotalUsersCount(){
        return userRepository.getTotalUserCount();
    }
}