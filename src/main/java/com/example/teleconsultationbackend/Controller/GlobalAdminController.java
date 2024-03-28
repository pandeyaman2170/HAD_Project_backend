package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.JwtRequest;
import com.example.teleconsultationbackend.Entity.JwtResponse;
import com.example.teleconsultationbackend.Service.GlobalAdminService;
import com.example.teleconsultationbackend.Service.UserAuthenticationService;
import com.example.teleconsultationbackend.Utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/global_admin")
public class GlobalAdminController {
    @Autowired
    private GlobalAdminService globalAdminService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthenticationService userService;

    @PostMapping("/add/{admin_id}")
    public String addHospital(@PathVariable Long admin_id, @RequestBody Hospital hospital)
    {
        globalAdminService.createHospital(admin_id,hospital);
        return "done";
    }

    @GetMapping("/viewallHospital/{admin_id}")
    public List<Hospital> viewHospital(@PathVariable Long admin_id)
    {
        return globalAdminService.viewAllHospital(admin_id);

    }

    @PutMapping("/update/{admin_id}/{hospital_id}")
    public String updateHospital(@PathVariable Long admin_id,@PathVariable Long hospital_id,@RequestBody Hospital hospital)
    {
        globalAdminService.updateHospital(admin_id,hospital_id,hospital);
        return "Updated";

    }

    @DeleteMapping("/delete/{admin_id}/{hospital_id}")
    public String deleteHospital(@PathVariable Long admin_id,@PathVariable Long hospital_id)
    {
        globalAdminService.deleteHospital(admin_id,hospital_id);
        return "Deleted";

    }


    @PostMapping("/login")
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

    //--------------------------query for data visualization.---------------------------------
    @PreAuthorize("hasRole('ROLE_GLOBALADMIN')")
    @GetMapping("/totalHospitals")
    public int totalHospitals()
    {
        return globalAdminService.totalHospitals();
    }

    @GetMapping("/totalDoctors")
    public int totalDoctors()
    {
        return globalAdminService.totalDoctors();
    }

    @GetMapping("/totalPatients")
    public int totalPatients()
    {
        return globalAdminService.totalPatients();
    }

    @GetMapping("/getGlobalAdminDetails/{userName}")
    public GlobalAdmin getGlobalAdmin(@PathVariable String userName){
        System.out.println("GlobalAdmin: "+userName);
        return globalAdminService.getGlobalAdminByUserName(userName);
    };



}
