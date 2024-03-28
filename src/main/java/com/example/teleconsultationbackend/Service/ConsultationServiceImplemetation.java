package com.example.teleconsultationbackend.Service;


import com.example.teleconsultationbackend.DTO.DateWiseConsultations;
import com.example.teleconsultationbackend.DTO.MonthWiseConsultation;
import com.example.teleconsultationbackend.Entity.Consultation;
import com.example.teleconsultationbackend.Repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ConsultationServiceImplemetation implements ConsultationService{

    @Autowired
    ConsultationRepository consultationRepository;
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

    public List<MonthWiseConsultation> totalMonthWiseConsultations() {
        List<Consultation> consultationList = consultationRepository.findAll();

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






}
