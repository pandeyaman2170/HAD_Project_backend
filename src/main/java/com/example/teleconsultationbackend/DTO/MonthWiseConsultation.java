package com.example.teleconsultationbackend.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonthWiseConsultation {
    @JsonProperty("month")
    private String month;

    @JsonProperty("totalConsultations")
    private long totalConsultations;
}
