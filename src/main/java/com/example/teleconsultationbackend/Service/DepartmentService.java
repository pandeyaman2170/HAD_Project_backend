package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Hospital;

import java.util.List;

public interface DepartmentService {
    Long getDepartmentIdByDepartmentName(String depName);
    List<Hospital>  getAllHospitals(Long department_id);
}
