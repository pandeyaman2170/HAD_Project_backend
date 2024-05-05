package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;

public interface DoctorService {
    DoctorDetails addDoctor(DoctorDetails doctorDetails);

    DoctorFetchDetails getDoctorByPhoneNumber(String phoneNumber);
    public int total_doctors();

    public DoctorFetchDetails updateDoctorDetails(DoctorFetchDetails doctorDetails);

    void setDoctorOnlineStatusHelper(Long doctorId);

    void setDoctorOfflineStatusHelper(Long doctorId);

    void updateDoctorDetails(Long doctorId, DoctorFetchDetails doctorFetchDetails);

    String deactivateDoctor(Long doctorId);
}
