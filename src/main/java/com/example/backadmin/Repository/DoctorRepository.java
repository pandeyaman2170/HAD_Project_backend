package com.example.backadmin.Repository;

import com.example.backadmin.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorRepository extends JpaRepository<Doctor,Long> {

}
