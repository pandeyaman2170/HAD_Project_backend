package com.example.teleconsultationbackend.Service;

import com.example.teleconsultationbackend.DTO.PrescriptionDetails;
import com.example.teleconsultationbackend.Entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class PdfServiceImplementationTest {

    @Mock
    private PrescriptionService prescriptionService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PdfServiceImplementation pdfService;

    private PrescriptionDetails prescriptionDetails;
    private Patient patient;
    private User user;
    private Doctor doctor;
    private Department department;
    private Hospital hospital;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up User
        user = new User();
        user.setPhone("1234567890");
        user.setGender('M');

        // Set up Hospital
        hospital = new Hospital();
        hospital.setName("Test Hospital");

        // Set up Department
        department = new Department();
        department.setName("Cardiology");

        // Set up Doctor
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setRegistrationNumber("DOC123");
        doctor.setHospital(hospital);
        doctor.setDepartment(department);
        doctor.setUser(user);

        // Set up Patient
        patient = new Patient();
        patient.setId(1L);
        patient.setBlood_group("O+");
        patient.setUser(user);

        // Set up PrescriptionDetails
        prescriptionDetails = new PrescriptionDetails();
        prescriptionDetails.setPrescriptionId(1);
        prescriptionDetails.setPatientId(1L);
        prescriptionDetails.setPatientName("John Doe");
        prescriptionDetails.setDoctorId(1L);
        prescriptionDetails.setDoctorName("Dr. Smith");
        prescriptionDetails.setHospital_name("Test Hospital");
        prescriptionDetails.setDepartment_name("Cardiology");
        prescriptionDetails.setConsultationDate(new Date());
        prescriptionDetails.setObservation("Patient has fever");
        prescriptionDetails.setRemark("Take rest for 2 days");

        Set<String> medicines = new HashSet<>();
        medicines.add("Paracetamol-->1-0-1");
        medicines.add("Vitamin C-->0-1-0");
        prescriptionDetails.setMedicine(medicines);
    }

    @Test
    void generatePdf_Success() {
        // Arrange
        when(prescriptionService.getPrescriptionById(anyInt())).thenReturn(prescriptionDetails);
        when(patientService.getPatientByPatientId(anyLong())).thenReturn(patient);

        // Act
        ByteArrayInputStream result = pdfService.generatePdf(1);

        // Assert
        assertNotNull(result);
        assertTrue(result.available() > 0);
    }

    @Test
    void generatePdf_PrescriptionNotFound() {
        // Arrange
        when(prescriptionService.getPrescriptionById(anyInt())).thenReturn(null);

        // Act
        ByteArrayInputStream result = pdfService.generatePdf(1);

        // Assert
        assertNull(result);
    }

    @Test
    void generatePdf_WithEmptyMedicines() {
        // Arrange
        prescriptionDetails.setMedicine(new HashSet<>());
        when(prescriptionService.getPrescriptionById(anyInt())).thenReturn(prescriptionDetails);
        when(patientService.getPatientByPatientId(anyLong())).thenReturn(patient);

        // Act
        ByteArrayInputStream result = pdfService.generatePdf(1);

        // Assert
        assertNotNull(result);
        assertTrue(result.available() > 0);
    }

    @Test
    void generatePdf_WithNullPatient() {
        // Arrange
        when(prescriptionService.getPrescriptionById(anyInt())).thenReturn(prescriptionDetails);
        when(patientService.getPatientByPatientId(anyLong())).thenReturn(null);

        // Act
        ByteArrayInputStream result = pdfService.generatePdf(1);

        // Assert
        assertNull(result);
    }

    @Test
    void generatePdf_WithLongObservationAndRemark() {
        // Arrange
        prescriptionDetails.setObservation("Very long observation text ".repeat(50));
        prescriptionDetails.setRemark("Very long remark text ".repeat(50));
        when(prescriptionService.getPrescriptionById(anyInt())).thenReturn(prescriptionDetails);
        when(patientService.getPatientByPatientId(anyLong())).thenReturn(patient);

        // Act
        ByteArrayInputStream result = pdfService.generatePdf(1);

        // Assert
        assertNotNull(result);
        assertTrue(result.available() > 0);
    }
}