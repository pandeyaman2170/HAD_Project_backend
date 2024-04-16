package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/department/get_all_hospitals")
    public List<Hospital> getAllHospitals(Long department_id){
        return departmentService.getAllHospitals(department_id);
    }

    @GetMapping("/get_all_departments_from_all_hospitals")
    public List<Department> getAllDepartmentsFromAllHospitals(){
        return departmentRepository.findAll();
    }

    @GetMapping("/get_departmentId_by_departmentName/{depName}")
    public Long getDepartmentIdByDepartmentName(@PathVariable String depName){
        return departmentService.getDepartmentIdByDepartmentName(depName);
    }

    @GetMapping("/department/get_online_doctor/{depId}")
    public Long getOnlineDoctor(@PathVariable Long depId){
        return departmentService.getOnlineDoctorHelper(depId);
    }
}
