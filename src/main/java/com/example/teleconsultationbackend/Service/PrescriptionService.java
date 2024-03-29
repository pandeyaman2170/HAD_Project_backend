package com.example.teleconsultationbackend.Service;
import com.example.teleconsultationbackend.DTO.DailyLogDetails;
import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import java.util.List;

public interface PrescriptionService {
    public PrescriptionDetails addPrescription(PrescriptionDetails prescriptionDetails);
    public List<PrescriptionDetails> getPrescriptionsPatient(long patientId);

    public List<PrescriptionDetails> getPrescriptionsDoctor(long patientId);

    public PrescriptionDetails getPrescriptionById(int prescriptionId);

    public List<DailyLogDetails> doctorDailyLog(long doctorId);


}

