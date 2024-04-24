package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.Patient;

public interface ShareRecordService {
    void createShareRecord(Hospital sending, Hospital receiving);

}
