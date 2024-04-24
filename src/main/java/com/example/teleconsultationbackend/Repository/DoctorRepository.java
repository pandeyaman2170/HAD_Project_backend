package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    //public Doctor findByEmail(String email);

//    @Query("select d FROM Doctor d WHERE d.user.id = ?1")
    Doctor findDoctorByUserId(Long id);
    @Query("SELECT d FROM Doctor d WHERE d.user.phone = ?1")
    Doctor findByPhoneNumber(String phoneNo);
    //public Doctor findByPhone(String phoneNumber);

    @Query("SELECT count(*) FROM Doctor d")
    int get_count();

    Doctor findDoctorById(Long doctorId);

    @Query("SELECT d FROM Doctor d WHERE d.hospital.hospital_id=?1")
    List<Doctor> getDoctorsByHospitalID(Long hospitalId);
}
