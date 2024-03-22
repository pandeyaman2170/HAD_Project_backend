package com.example.teleconsultationbackend.Controller;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.HospitalService;
import com.example.teleconsultationbackend.Service.HospitalServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalServiceImplementation hospitalservice;

    @PostMapping("/hospital_admin/login")
    public String login(@RequestBody Hospital loginRequest) {
        return hospitalservice.adminlogin(loginRequest.getPhone());
    }

    @PostMapping("/hospital_admin/add_doctor/{hospital_id}")
    public String addDoctor(@PathVariable Long hospital_id, @RequestBody Doctor doctor){
        System.out.println(doctor);
        hospitalService.addDoctor(hospital_id,doctor);
        return "doctor added succesfully!!";
    }

    @PostMapping("/hospital_admin/add_depertment/{hospital_id}")
    public String adddepartment(@PathVariable Long hospital_id,
                                @RequestBody Department department){
        System.out.println(department);
        hospitalService.addDepartments(hospital_id,department);
        return "Department Added sussefully";
    }

//    @PostMapping("/hospital_admin/add_doctor/{hospital_id}")
//    public String addHospital(@PathVariable Long hospital_id, @RequestBody Doctor doctor){
//        hospitalService.createDoctor(hospital_id, doctor);
//        return "Done";
//    }
}
