package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Service.QueueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Tag(
        name="Queue APIs"
)

@RequestMapping("/queues")
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

    @GetMapping("/waiting_list_by_departmentId/{depId}")
    public int getAllWaitingPatientByInQueueByDepartmentId(@PathVariable Long depId){
        return queueService.getAllWaitingPatientByInQueueByDepartmentId(depId);
    }

    @GetMapping("/get_waiting_patients_by_departmentName/{depName}")
    public List<Patient> getAllWaitingPatientByInQueueByDepartmentId(@PathVariable String depName){
        System.out.println(depName+"*************");
        return queueService.getAllPatientByDepNameQueue(depName);
    }

    @GetMapping("/get_queue_top_patient_id_by_depName/{depName}")
    public Long getQueuesTopPatientIdByDepartmentName(@PathVariable String depName){
        return queueService.getQueuesTop(depName);
    }

    @GetMapping("/get_queue_size_by_departmentId/{depName}")
    public int getQueueSize(@PathVariable String depName){
        return queueService.getQueueSizeHelper(depName);
    }
}
