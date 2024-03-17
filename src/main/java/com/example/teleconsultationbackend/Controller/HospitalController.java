package com.example.teleconsultationbackend.Controller;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("add/{admin_id}")
    public String addHospital(@PathVariable Long admin_id, @RequestBody Hospital hospital)
    {
        hospitalService.createHospital(admin_id,hospital);
        return "done";
    }

//    @PutMapping ("update/{admin_id}")
//    public String updateHospital(@PathVariable Long admin_id, @RequestBody Hospital hospital)
//    {
//        hospitalService.updateHospital(admin_id,hospital);
//        return "done";
//    }
//    @DeleteMapping("delete/{admin_id}")
//    public String deleteHospital(@PathVariable Long admin_id, @RequestBody Hospital hospital)
//    {
//        hospitalService.deleteHospital(admin_id,hospital);
//        return "done";
//    }
//    @GetMapping("view/{admin_id}")
//    public String viewHospital(@PathVariable Long admin_id, @RequestBody Hospital hospital)
//    {
//        hospitalService.viewHospital(admin_id,hospital);
//        return "done";
//    }

    @GetMapping("view/{admin_id}")
    public List<Hospital> viewHospital(@PathVariable Long admin_id)
    {
       return hospitalService.viewAllHospital(admin_id);

    }

    @PutMapping("update/{admin_id}/{hospital_id}")
    public String updateHospital(@PathVariable Long admin_id,@PathVariable Long hospital_id,@RequestBody Hospital hospital)
    {
        hospitalService.updateHospital(admin_id,hospital_id,hospital);
        return "Updated";

    }

    @DeleteMapping("delete/{admin_id}/{hospital_id}")
    public String deleteHospital(@PathVariable Long admin_id,@PathVariable Long hospital_id)
    {
        hospitalService.deleteHospital(admin_id,hospital_id);
        return "Deleted";

    }

}
