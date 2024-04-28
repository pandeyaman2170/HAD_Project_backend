package com.example.teleconsultationbackend.Controller;
import com.example.teleconsultationbackend.DTO.DoctorDetails;
import com.example.teleconsultationbackend.DTO.DoctorFetchDetails;
import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.Department;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Service.DoctorService;
import com.example.teleconsultationbackend.Entity.User;
import com.example.teleconsultationbackend.Service.HospitalService;
import com.example.teleconsultationbackend.Service.HospitalServiceImplementation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(
        name="Hospital APIs"
)
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalServiceImplementation hospitalservice;

    @Autowired
    private DoctorService doctorService;
    @PostMapping("/hospital_admin/add_doctor")
    public String addDoctor(@RequestBody DoctorDetails doctorDetails){
//        doctorDetails.setRole("doctor");
        doctorService.addDoctor(doctorDetails);
        return "doctor added succesfully!!";
    }

    @PostMapping("/hospital_admin/add_depertment/{hospital_id}")
    public String addDepartmentInHospital(@PathVariable Long hospital_id,
                                @RequestBody Department department){
        String departmentName = department.getName();
        hospitalService.addDepartments(hospital_id, departmentName);
        return "Department Added successfully";
    }

    @GetMapping("/get_all_departments/{hospital_id}")
    public List<Department> getDepartments(@PathVariable Long hospital_id){
        return hospitalService.getAllDepartments(hospital_id);
    }

    @GetMapping("/hospital_admin/getHospitalAdminDetails/{username}")
    public Hospital getHospitalDetail(@PathVariable String username){
        return hospitalService.getHospitalByUserName(username);
    }

    @GetMapping("/hospital_admin/get_doctor_list/{hospital_id}")
    public List<DoctorFetchDetails> getDoctorsListOfAHospital(@PathVariable Long hospital_id){
        return hospitalService.getDoctorsListOfAHospital(hospital_id);
    }

    @GetMapping("/hospital_admin/get_department_list/{hospital_id}")
    public List<Department> getHospitalDepartments(@PathVariable Long hospital_id){
        return hospitalService.getHospitalDepartments(hospital_id);
    }

    @GetMapping("hospital_admin/get_consultation_list/{hospital_id}")
    public List<PrescriptionDetails> getHospitalTotalConsultations(@PathVariable Long hospital_id){
        return hospitalService.getHospitalTotalConsultations(hospital_id);
    }

}
