package com.example.teleconsultationbackend.Controller;

import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Service.ShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
