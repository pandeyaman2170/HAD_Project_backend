package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class QueuesController {

    private final QueueService queueService;

    @Autowired
    public QueuesController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/list-of-all-doctors/{dep_id}")
    public List<Doctor> listOfAllDoctorsInDepartmentOfAllHospitals(
            @PathVariable Long dep_id) {
        return queueService.getAllDoctorsFromDepartment(dep_id);
    }
}
