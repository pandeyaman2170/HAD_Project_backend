package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;

public interface DoctorService {
    DoctorDetails addDoctor(DoctorDetails doctorDetails);

    DoctorFetchDetails getDoctorByPhoneNumber(String phoneNumber);
}
