package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalInterface extends JpaRepository<Hospital,Long> {
}
