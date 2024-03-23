package com.example.teleconsultationbackend.Service;

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
}
