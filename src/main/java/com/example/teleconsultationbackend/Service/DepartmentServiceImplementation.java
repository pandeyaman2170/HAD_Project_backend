package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class DepartmentServiceImplementation implements DepartmentService{

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public List<Hospital> getAllHospitals(Long department_id){
        return departmentRepository.findHospitalsByDepartmentID(department_id);
    }

    @Override
    @Transactional
    public Long getDepartmentIdByDepartmentName(String depName){
        Department department = departmentRepository.findDepartmentByName(depName);
        if(department != null){
            return department.getId();
        }else{
            return null;
        }
    }

    @Override
    @Transactional
    public Long getOnlineDoctorHelper(Long depId){
        Department department = departmentRepository.findDepartmentById(depId);
        for(Doctor doctor:department.getDoctors()){
            if(doctor.isOnline_status()){
                return doctor.getId();
            }
        }
        return null;
    }
}
