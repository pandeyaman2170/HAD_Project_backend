package com.example.backadmin.Controller;

import com.example.backadmin.Entity.Doctor;
import com.example.backadmin.Services.DoctorService;
import com.example.backadmin.Services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DoctorService doctorService;

//    @GetMapping
//    public List<Hospital> getAllHospitals() {
//        return hospitalService.getAllHospitals();
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
//        Optional<Hospital> hospitalOptional = hospitalService.getHospitalById(id);
//        return hospitalOptional.map(hospital -> new ResponseEntity<>(hospital, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping
//    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
//        Hospital createdHospital = hospitalService.createHospital(hospital);
//        System.out.println("+++++++++++++++++++++");
//        return new ResponseEntity<>(createdHospital, HttpStatus.CREATED);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Hospital> updateHospital(@RequestBody Hospital updatedHospital, @PathVariable Long id) {
//        Hospital updatedHospitalResult = hospitalService.updateHospital(updatedHospital, id);
//        return new ResponseEntity<>(updatedHospitalResult, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
//        hospitalService.deleteHospital(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    @PostMapping("/{hospitalId}/doctors")
//    public ResponseEntity<Hospital> addDoctorToHospital(@PathVariable Long hospitalId, @RequestBody Doctor doctor) {
//        Hospital hospital = hospitalService.addDoctorToHospital(hospitalId, doctor);
//        return new ResponseEntity<>(hospital, HttpStatus.CREATED);
//    }
    @PostMapping("/hospital_admin/add_doctor/{hospital_id}")
    public String addDoctor(@PathVariable Long hospital_id, @RequestBody Doctor doctor){
        hospitalService.createDoctor(hospital_id, doctor);
        return "Done";
    }
//    @PostMapping("/hospital_admin/add_doctor")
//    public String addDoctor1(@RequestBody){
//
//    }

}
