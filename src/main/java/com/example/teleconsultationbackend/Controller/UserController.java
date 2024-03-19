package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        return userService.login(loginRequest.getPhone(), loginRequest.getRole());
    }
}
