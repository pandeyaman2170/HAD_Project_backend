package com.example.teleconsultationbackend.DTO;

import com.example.teleconsultationbackend.Entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalCompleteDetail {
    private Long hospital_id;
    private String name;
    private String location;
    private String phone;
    List<Department> departments;
    List<DoctorFetchDetails> doctorFetchDetails;
}
