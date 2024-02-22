package com.example.backadmin.Controller;

import com.example.backadmin.Entity.Hospital;
import com.example.backadmin.Services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
        Optional<Hospital> hospitalOptional = hospitalService.getHospitalById(id);
        return hospitalOptional.map(hospital -> new ResponseEntity<>(hospital, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
        Hospital createdHospital = hospitalService.createHospital(hospital);
        System.out.println("+++++++++++++++++++++");
        return new ResponseEntity<>(createdHospital, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@RequestBody Hospital updatedHospital, @PathVariable Long id) {
        Hospital updatedHospitalResult = hospitalService.updateHospital(updatedHospital, id);
        return new ResponseEntity<>(updatedHospitalResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
