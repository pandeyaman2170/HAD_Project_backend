package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;

public interface HospitalService {
    void createHospital(Long admin_id,Hospital hospital);
    void addDoctor(Long hospitalId, Doctor doctor);
    void addDepartments(Long hospitalId, Department department);
}
