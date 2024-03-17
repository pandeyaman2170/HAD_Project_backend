package com.example.backadmin.Services;

import com.example.backadmin.Entity.Doctor;

public interface HospitalService {

    void createDoctor(Long hospitalId, Doctor doctor);
}
