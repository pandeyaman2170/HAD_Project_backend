package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String login(String phone, String role) {
        User user = userRepository.findByPhoneAndRole(phone, role);
        if (user != null) {
            return "OK";
        } else {
            return "Error: User not found";
        }
    }
}
