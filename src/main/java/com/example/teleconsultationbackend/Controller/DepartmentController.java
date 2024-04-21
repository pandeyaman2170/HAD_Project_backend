package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Repository.DepartmentRepository;
import com.example.teleconsultationbackend.Service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(
        name="Department APIs"
)
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

}
