package com.example.teleconsultationbackend.Controller;
import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Service.DoctorService;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.HospitalService;
import com.example.teleconsultationbackend.Service.HospitalServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalServiceImplementation hospitalservice;

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/hospital_admin/login")
    public String login(@RequestBody Hospital loginRequest) {
        return hospitalservice.adminlogin(loginRequest.getPhone());
    }

    @PostMapping("/hospital_admin/add_doctor")
    public String addDoctor(@RequestBody DoctorDetails doctorDetails){
        doctorDetails.setRole("doctor");
        doctorService.addDoctor(doctorDetails);
        return "doctor added succesfully!!";
    }

    @PostMapping("/hospital_admin/add_depertment/{hospital_id}")
    public String adddepartment(@PathVariable Long hospital_id,
                                @RequestBody Department department){
        System.out.println(department);
        hospitalService.addDepartments(hospital_id,department);
        return "Department Added sussefully";
    }

    @GetMapping("/get_all_departments/{hospital_id}")
    public List<Department> getDepartments(@PathVariable Long hospital_id){
        return hospitalService.getAllDepartments(hospital_id);
    }

}
