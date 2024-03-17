package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;

import java.util.List;

public interface HospitalService {
    void createHospital(Long admin_id,Hospital hospital);

//    void deleteHospital(Long admin_id,Hospital hospital);
    List<Hospital> viewAllHospital(Long admin_id);
    //List<Hospital> viewAllHospitalId(Long admin_id);

    void updateHospital(Long admin_id, Long hospital_id,Hospital hospital);
    void deleteHospital(Long admin_id, Long hospital_id);









}
