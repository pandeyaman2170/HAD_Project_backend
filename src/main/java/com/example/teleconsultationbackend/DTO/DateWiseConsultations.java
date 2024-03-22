package com.example.teleconsultationbackend.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class DateWiseConsultations {

        @Temporal(TemporalType.DATE)
        private Date dateOfConsultation;

        private long totalConsultations;
    }

