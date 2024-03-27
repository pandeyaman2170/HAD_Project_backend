package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalAdminService {
    void createHospital(Long admin_id,Hospital hospital);

    List<Hospital> viewAllHospital(Long admin_id);

    void updateHospital(Long admin_id, Long hospital_id,Hospital hospital);

    void deleteHospital(Long admin_id, Long hospital_id);

    int totalHospitals();
    int totalDoctors();
    int totalPatients();
}
