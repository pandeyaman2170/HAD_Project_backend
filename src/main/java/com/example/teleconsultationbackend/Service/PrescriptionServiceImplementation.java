package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DailyLogDetails;
import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.*;
import com.example.teleconsultationbackend.Repository.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                Consultation consultation = consultationRepository.findConsultationByDoctorAndPatientAndStatus(prescribingDoctor, prescribedPatient, "accepted");
                consultation.setStatus("ended");
                consultationRepository.save(consultation);
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
                    prescription.getPatient().getUser().getFirstName()+" "+prescription.getPatient().getUser().getLastName(),prescription.getPatient().getId(), prescription.getFollowUpDate(),prescription.getDoctor().getHospital().getName(),prescription.getDoctor().getDepartment().getName()));
        }
        return prescriptionDetailsList;

    }
    @Override
    public PrescriptionDetails getPrescriptionById(int prescriptionId) {

        Prescription prescription;
        try {
            if(prescriptionRepository.findById(prescriptionId).isPresent()) {
                prescription = prescriptionRepository.findById(prescriptionId).get();
//                String hospital_name= prescription.getDoctor().getHospital().getName();
//                String department_name = prescription.getDoctor().getDepartment().getName();

                return new PrescriptionDetails(prescription.getPrescriptionId(),
                        prescription.getConsultationDate(), prescription.getMedical_findings(),
                        prescription.getMedicine(),
                        prescription.getRemark(),
                        prescription.getDoctor().getUser().getFirstName() + " " + prescription.getDoctor().getUser().getLastName(),
                        prescription.getDoctor().getId(),
                        prescription.getPatient().getUser().getFirstName() + " " + prescription.getPatient().getUser().getLastName(),
                        prescription.getPatient().getId(),
                        prescription.getFollowUpDate(),prescription.getDoctor().getHospital().getName(),prescription.getDoctor().getDepartment().getName());
            }
            return null;

        } catch (Exception e) {
            System.out.println("Error Occurred in getting prescription by prescriptionId");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DailyLogDetails> doctorDailyLog(long doctorId) {

        List<Prescription> prescriptionList = prescriptionRepository.findPrescriptionsByDoctor_DoctorId(doctorId);

        List<DailyLogDetails> dailyLogDetailsList = new ArrayList<>();

        // We'll compare the dates in string format, we'll convert consultation date and current date to the below pattern
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(prescriptionList != null) {

            String currentDate = dateFormat.format(new Date());

            for(Prescription prescription: prescriptionList) {
                String consultationDate = dateFormat.format(prescription.getConsultationDate());
                if(currentDate.equals(consultationDate)) {
                    dailyLogDetailsList.add(new DailyLogDetails(
                            prescription.getDoctor().getId(),
                            prescription.getConsultationDate(),
                            prescription.getPatient().getId(),
                            prescription.getMedical_findings(),
                            prescription.getRemark()
                    ));
                }
            }
            return dailyLogDetailsList;
        }
        return null;
    }



}

