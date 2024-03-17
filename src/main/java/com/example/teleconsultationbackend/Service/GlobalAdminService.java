package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalAdminService {
    void createHospital(GlobalAdmin globalAdmin);
}
