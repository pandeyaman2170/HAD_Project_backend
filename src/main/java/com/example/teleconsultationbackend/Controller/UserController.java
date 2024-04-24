package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.JwtRequest;
import com.example.teleconsultationbackend.DTO.JwtResponse;
import com.example.teleconsultationbackend.DTO.UserLoginStatus;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.UserAuthenticationService;
import com.example.teleconsultationbackend.Service.UserService;
import com.example.teleconsultationbackend.Utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @GetMapping ("/login/{phone_num}/{role}")
    public UserLoginStatus login(@PathVariable String role, @PathVariable String phone_num) throws NoSuchAlgorithmException {
        try {
            return userService.login(phone_num, role);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get_total_users")
    public Long getAllUsers(){
        return userService.getTotalUsersCount();
    }

    @GetMapping("/get_all_phone_numbers")
    public List<User> getTotalUsersDetails() throws Exception {return userService.getTotalUsersDetails(); }

    @PostMapping("/hospital_admin/login")
    public JwtResponse hospitalAdmin(@RequestBody JwtRequest jwtRequest) throws Exception{
        System.out.println("Hello");
        System.out.println(jwtRequest.getUsername());
        System.out.println(jwtRequest.getPassword());
        try {
            System.out.println("in try");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
            System.out.println("in try");
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userAuthenticationService.loadUserByUsername(jwtRequest.getUsername());
        System.out.println(userDetails);
        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }
}