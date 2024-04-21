package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.UserLoginStatus;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(
        name="User APIs"
)

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("/login/{phone_num}/{role}")
    public UserLoginStatus login(@PathVariable String role, @PathVariable String phone_num){
        return userService.login(phone_num, role);
    }

    @GetMapping("/get_total_users")
    public Long getAllUsers(){
        return userService.getTotalUsersCount();
    }
}