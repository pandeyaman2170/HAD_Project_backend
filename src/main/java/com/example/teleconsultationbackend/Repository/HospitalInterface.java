package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HospitalInterface extends JpaRepository<Hospital,Long> {

    @Query("Select h from Hospital h where h.hospital_id=?1")
    Hospital findHospitalById(Long hospital_id);
}
