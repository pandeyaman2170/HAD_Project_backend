package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Service.ShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ShareRecordController {

    @Autowired
    private ShareRecordService shareRecordService;


    @PostMapping("/share-hospital-record")
    public void shareRecordHospital(@RequestBody Map<String, Hospital> hospitalMap) {
        Hospital sending = hospitalMap.get("sending");
        Hospital receiving = hospitalMap.get("receiving");
        shareRecordService.createShareRecord(sending, receiving);
        System.out.println("done");
    }

}
