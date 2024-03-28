package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.UserLoginStatus;
import com.example.teleconsultationbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("/login/{phone_num}/{role}")
    public UserLoginStatus login(@PathVariable String role, @PathVariable String phone_num){
        return userService.login(phone_num, role);
    }
}