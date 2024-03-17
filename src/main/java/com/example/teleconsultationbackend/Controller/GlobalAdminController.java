package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Service.GlobalAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalAdminController {
    @Autowired
    private GlobalAdminService globalAdminService;

    @PostMapping("/addHospital")
    public String addHospital(@RequestBody GlobalAdmin globalAdmin){
        globalAdminService.createHospital(globalAdmin);
        return "HospitalDone";
    }
}
