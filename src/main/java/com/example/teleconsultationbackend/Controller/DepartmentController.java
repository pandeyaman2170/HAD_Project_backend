package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/department/get_all_hospitals")
    public List<Hospital> getAllHospitals(Long department_id){
        return departmentService.getAllHospitals(department_id);
    }

}
