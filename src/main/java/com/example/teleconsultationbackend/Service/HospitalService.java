package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;

import java.util.List;

public interface HospitalService {
    void createHospital(Long admin_id,Hospital hospital);
//    void addDoctor(Long hospitalId, Long departmentID, Doctor doctor);

    void addDepartments(Long hospitalId, String departmentName);

    String adminlogin(String phone);

    List<Department> getAllDepartments(Long hospital_id);
    public int total_hospitals();

    Hospital getHospitalByUserName(String username);

    List<DoctorFetchDetails> getDoctorsListOfAHospital(Long hospitalId);

    List<Department> getHospitalDepartments(Long hospitalId);

    List<PrescriptionDetails> getHospitalTotalConsultations(Long hospitalId);
}
