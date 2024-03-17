package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorInterface extends JpaRepository<Doctor, Long> {
}
