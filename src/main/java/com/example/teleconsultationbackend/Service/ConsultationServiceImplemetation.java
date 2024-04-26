package com.example.teleconsultationbackend.Service;


import com.example.teleconsultationbackend.DTO.ConsultationDetails;
import com.example.teleconsultationbackend.DTO.DateWiseConsultations;
import com.example.teleconsultationbackend.DTO.MonthWiseConsultation;
import com.example.teleconsultationbackend.Entity.Consultation;
import com.example.teleconsultationbackend.Entity.Doctor;
import com.example.teleconsultationbackend.Entity.Patient;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import com.example.teleconsultationbackend.Repository.DoctorRepository;
import com.example.teleconsultationbackend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ConsultationServiceImplemetation implements ConsultationService{

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public int total_consultation()
    {
        return consultationRepository.get_count();
    }

    @Override
    public List<DateWiseConsultations> totalDateWiseConsultations() {
        List<Consultation> consultationList = consultationRepository.findAll();

        List<DateWiseConsultations> dateWiseConsultationsList = new ArrayList<>();

        Map<Date, Long> totalConsultations = new TreeMap<>();

        for(Consultation consultation: consultationList) {
            totalConsultations.merge(consultation.getConsultationDate(), 1L, Long::sum);
        }

        for (Map.Entry<Date, Long> entry : totalConsultations.entrySet()) {
            Date key = entry.getKey();
            Long value = entry.getValue();
            dateWiseConsultationsList.add(
                    new DateWiseConsultations(key, value)
            );
        }

        return dateWiseConsultationsList;
    }
    public List<MonthWiseConsultation> totalMonthWiseConsultations(Long doctorId) {
        List<Consultation> consultationList = consultationRepository.findConsultationByDoctor_Id(doctorId);
        Map<String, Long> totalConsultations = new TreeMap<>();

        // Aggregate consultations by month
        for (Consultation consultation : consultationList) {
            // Extract month from date
            String month = extractMonth(consultation.getConsultationDate());
            // Aggregate consultations for each month
            totalConsultations.merge(month, 1L, Long::sum);
        }

        List<MonthWiseConsultation> monthWiseConsultationsList = new ArrayList<>();
        // Convert aggregated data to list of MonthWiseConsultations objects
        for (Map.Entry<String, Long> entry : totalConsultations.entrySet()) {
            String month = entry.getKey();
            Long consultations = entry.getValue();
            System.out.println(month+" "+consultations);
            monthWiseConsultationsList.add(new MonthWiseConsultation(month, consultations));
        }

        return monthWiseConsultationsList;
    }

    // Method to extract month from date
    private String extractMonth(Date date) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        return monthFormat.format(date);
    }


    @Override
    public Long totalConsultationByDoctor(Long doctorId) {
        return consultationRepository.findAllByDoctor_DoctorId(doctorId);
    }

    @Override
    public List<ConsultationDetails> totalConsultationByDep(Long depId, Long hospitalId) {
        List<Consultation> consultationList = consultationRepository.
                findConsultationsByDepIdAndStatus(depId, "accepted");
        List<ConsultationDetails> consultationDetailsList = new ArrayList<>();
        for (Consultation consultation : consultationList){
            if(Objects.equals(consultation.getDoctor().getHospital().getHospital_id(), hospitalId)){
                ConsultationDetails consultationDetails = new ConsultationDetails();
                consultationDetails.setConsultationId(consultation.getConsultationId());
                consultationDetails.setDoctorName(consultation.getDoctor().getUser().getFirstName());
                consultationDetails.setDoctorId(consultation.getDoctor().getId());
                consultationDetails.setPatientId(consultation.getPatient().getId());
                consultationDetails.setPatientName(consultation.getPatient().getUser().getFirstName());
                consultationDetails.setStatus(consultation.getStatus());
                consultationDetailsList.add(consultationDetails);
            }
        }
        return consultationDetailsList;
    }

    @Override
    public Long totalConsultationByPatient(Long patientId) {
        return consultationRepository.findAllByPatientId(patientId);
    }

    @Override
    public void addConsultationStatusWaitinghelper(Long patientId, Long depId) {
        Patient patient =  patientRepository.findPatientById(patientId);
        consultationRepository.save(
                new Consultation(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        null, patient,
                        "waiting",
                        depId)
        );
    }

    @Override
    public void setStatusToAcceptedHelper(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        Long depId = doctor.getDepartment().getId();
        for (Consultation consultation : consultationRepository.findAll()){
            if (Objects.equals(consultation.getDepId(), depId) &&
                    Objects.equals(consultation.getStatus(), "waiting")){
                consultation.setDoctor(doctor);
                consultation.setStatus("accepted");
                consultationRepository.save(consultation);
                break;
            }
        }
    }
}
