package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.GlobalAdmin;
import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Service.GlobalAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GlobalAdminController {
    @Autowired
    private GlobalAdminService globalAdminService;

//    @PostMapping("add/{admin_id}")
//    public String addHospital(@PathVariable Long admin_id, @RequestBody Hospital hospital)
//    {
//        globalAdminService.createHospital(admin_id,hospital);
//        return "done";
//    }

    @GetMapping("view/{admin_id}")
    public List<Hospital> viewHospital(@PathVariable Long admin_id)
    {
        return globalAdminService.viewAllHospital(admin_id);

    }

    @PutMapping("update/{admin_id}/{hospital_id}")
    public String updateHospital(@PathVariable Long admin_id,@PathVariable Long hospital_id,@RequestBody Hospital hospital)
    {
        globalAdminService.updateHospital(admin_id,hospital_id,hospital);
        return "Updated";

    }

    @DeleteMapping("delete/{admin_id}/{hospital_id}")
    public String deleteHospital(@PathVariable Long admin_id,@PathVariable Long hospital_id)
    {
        globalAdminService.deleteHospital(admin_id,hospital_id);
        return "Deleted";

    }
}
