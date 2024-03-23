package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.DateWiseConsultations;
import com.example.teleconsultationbackend.DTO.MonthWiseConsultation;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ConsultationService {

    public int total_consultation();
    List<DateWiseConsultations> totalDateWiseConsultations();
    List<MonthWiseConsultation> totalMonthWiseConsultations();




}
