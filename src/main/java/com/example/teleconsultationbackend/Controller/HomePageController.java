package com.example.teleconsultationbackend.Controller;
import com.example.teleconsultationbackend.Service.ConsultationService;
import com.example.teleconsultationbackend.Service.DoctorService;
import com.example.teleconsultationbackend.Service.HospitalService;
import com.example.teleconsultationbackend.Service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/homepage")
@Tag(
        name="Home page APIs"
)
public class HomePageController {
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
//    @Autowired
//    public HomePageController(ConsultationService consultationService) {
//        this.consultationService = consultationService;
//    }

//    public HomePageController(HospitalService hospitalService) {
//        this.hospitalService = hospitalService;
//    }
//    public HomePageController(PatientService patientService) {
//        this.patientService = patientService;
//    }
//    public HomePageController(DoctorService doctorService) {
//        this.doctorService = doctorService;
//    }

    @GetMapping("/total_consultation")
    public int getTotalConsultation() {
        return consultationService.total_consultation();
    }

    @GetMapping("/total_hospitals")
    public int getTotalHospitals() {

        return hospitalService.total_hospitals();
    }
//

    @GetMapping("/total_doctors")
    public int getTotalDoctors() {
        // Logic to fetch total doctors count from your data source
        return doctorService.total_doctors();
    }
//

    @GetMapping("/total_patients")
    public int getTotalPatients() {
        return patientService.total_patients();
    }
}
