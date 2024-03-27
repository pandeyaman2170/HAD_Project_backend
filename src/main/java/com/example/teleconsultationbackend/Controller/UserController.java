package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.PatientLoginStatus;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("/login/{phone_num}/{role}")
    public PatientLoginStatus login(@PathVariable String role, @PathVariable String phone_num){
        return userService.login(phone_num, role);
    }
}