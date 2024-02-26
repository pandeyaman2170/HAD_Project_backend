package com.example.backadmin.Controller;


import com.example.backadmin.Entity.Doctor;
import com.example.backadmin.Entity.Hospital;
import com.example.backadmin.Services.DoctorService;
import com.example.backadmin.Services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class DoctorController {
    @Autowired
    private DoctorService  doctorService;

    @GetMapping
    public List<Doctor> getAllDoctors() {return doctorService.getAllDoctors();}

    @GetMapping("/get_doctor_list/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctorOptional = doctorService.getDoctorById(id);
        return doctorOptional.map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add_doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        System.out.println("+++++++++++++++++++++");
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }
    @PutMapping("/update_doctor/{id}")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor updatedDoctor, @PathVariable Long id) {
        Doctor updatedDoctorResult = doctorService.updateDoctor(updatedDoctor, id);
        return new ResponseEntity<>(updatedDoctorResult, HttpStatus.OK);
    }
    @DeleteMapping("/remove_doctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
