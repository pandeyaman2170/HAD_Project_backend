package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
}
