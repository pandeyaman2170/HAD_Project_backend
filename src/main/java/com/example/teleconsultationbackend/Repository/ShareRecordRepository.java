package com.example.teleconsultationbackend.Repository;

import com.example.teleconsultationbackend.Entity.Hospital;
import com.example.teleconsultationbackend.Entity.ShareRecordHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRecordRepository extends JpaRepository<ShareRecordHospital, Long> {
    @Query("select s from ShareRecordHospital s where s.patientId=:patientId and s.sending_hospital=:sendingHospital and s.receiving_hospital=:recievingHospital")
    ShareRecordHospital findShareRecordHospitalByPatientIdAndSending_hospitalAndReceiving_hospital(
            Long patientId, Hospital sendingHospital, Hospital recievingHospital);

}
