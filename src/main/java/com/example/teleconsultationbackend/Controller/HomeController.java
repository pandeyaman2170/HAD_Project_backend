package com.example.teleconsultationbackend.Controller;


import com.example.teleconsultationbackend.DTO.JwtRequest;
import com.example.teleconsultationbackend.DTO.JwtResponse;
import com.example.teleconsultationbackend.Service.UserAuthenticationService;
import com.example.teleconsultationbackend.Utility.JWTUtility;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Tag(
        name="User Authentication API"
)
public class HomeController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthenticationService userService;

    //@RequestBody JwtRequest jwtRequest
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
        System.out.println("hello");
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
                = userService.loadUserByUsername(jwtRequest.getUsername());
        System.out.println(userDetails);
        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }

}
