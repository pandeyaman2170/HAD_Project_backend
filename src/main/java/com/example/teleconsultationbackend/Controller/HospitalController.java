package com.example.teleconsultationbackend.Controller;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("add/{admin_id}")
    public String addDoctor(@PathVariable Long admin_id, @RequestBody Hospital hospital)
    {
        hospitalService.createHospital(admin_id,hospital);
        return "done";
    }
}
