package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.DailyLogDetails;
import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.Service.DoctorService;
import com.example.teleconsultationbackend.Service.PrescriptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PrescriptionService prescriptionService;

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getDoctorByPhoneNumber/{phoneNumber}")
    public DoctorFetchDetails getDoctorByPhoneNumber(@PathVariable String phoneNumber) {

        // When otp is verified the doctor details are fetched and sent to the front-end using this API, also this will set the isOnline doctor to TRUE.
        return doctorService.getDoctorByPhoneNumber(phoneNumber);
    }

    @SecurityRequirement( name = "Bearer Authentication")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/doctorDailyLog/{doctorId}")
    public List<DailyLogDetails> doctorDailyLog(@PathVariable String doctorId) {
        return prescriptionService.doctorDailyLog(Long.parseLong(doctorId));
    }

}
