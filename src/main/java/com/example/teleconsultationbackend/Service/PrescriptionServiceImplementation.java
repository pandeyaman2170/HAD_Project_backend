package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.Consultation;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Entity.Prescription;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import com.example.teleconsultationbackend.Repository.PrescriptionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class PrescriptionServiceImplementation implements PrescriptionService{
    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ConsultationRepository consultationRepository;
    @Transactional
    @Override
    public PrescriptionDetails addPrescription(PrescriptionDetails prescriptionDetails) {

        Doctor prescribingDoctor;
        Patient prescribedPatient;

        try {
            if(doctorRepository.findById(prescriptionDetails.getDoctorId()).isPresent() &&
                    patientRepository.findById(prescriptionDetails.getPatientId()).isPresent()) {

                prescribingDoctor = doctorRepository.findById(prescriptionDetails.getDoctorId()).get();
                prescribedPatient = patientRepository.findById(prescriptionDetails.getPatientId()).get();

                Prescription prescription = new Prescription(
                        prescriptionDetails.getConsultationDate(),
                        prescriptionDetails.getObservation(),
                        prescriptionDetails.getMedicine(),
                        prescriptionDetails.getRemark(),
                        prescriptionDetails.getFollowUpDate(),
                        prescribingDoctor,
                        prescribedPatient
                );

                prescriptionRepository.save(prescription);
                consultationRepository.save(
                        new Consultation(prescriptionDetails.getConsultationDate(),
                                prescribingDoctor,
                                prescribedPatient)
                );

                return prescriptionDetails;
            }
            else return null;

        } catch (Exception e) {
            System.out.println("Error Occurred while saving the prescription.");
            e.printStackTrace();
            return null;
        }
    }

    // --------------------- Get Prescription of a patient based on his ID by patient --------------------------------
    @Override
    public List<PrescriptionDetails> getPrescriptionsPatient(long patientId) {
        return getPrescriptions(patientId);
    }

    @Override
    public List<PrescriptionDetails> getPrescriptionsDoctor(long patientId) {
        return getPrescriptions(patientId);
    }

    private List<PrescriptionDetails> getPrescriptions(long patientId)
    {
        List<Prescription> prescriptionList = prescriptionRepository.findPrescriptionsByPatient_PatientId(patientId);
        List<PrescriptionDetails> prescriptionDetailsList = new ArrayList<>();
        for (Prescription prescription : prescriptionList) {
            prescriptionDetailsList.add(new PrescriptionDetails(prescription.getPrescriptionId(),prescription.getConsultationDate(),prescription.getMedical_findings(),
                    prescription.getMedicine(),prescription.getRemark(),prescription.getDoctor().getUser().getFirstName()+" " +prescription.getDoctor().getUser().getLastName(),prescription.getDoctor().getId(),
                    prescription.getPatient().getUser().getFirstName()+" "+prescription.getPatient().getUser().getLastName(),prescription.getPatient().getId(), prescription.getFollowUpDate()));
        }
        return prescriptionDetailsList;

    }



}

